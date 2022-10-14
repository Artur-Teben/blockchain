package blockchain.client;

import blockchain.model.Blockchain;
import blockchain.model.BlockchainType;
import blockchain.model.block.factory.BlockFactory;
import blockchain.service.BlockchainService;
import blockchain.service.impl.CryptocurrencyBlockchainService;
import blockchain.service.DataManager;
import blockchain.service.impl.MessengerBlockchainService;
import blockchain.service.impl.MessageManager;
import blockchain.service.impl.TransactionManager;
import blockchain.utils.SerializationUtils;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static blockchain.config.Configuration.BLOCKCHAIN_POOL_SIZE;
import static blockchain.config.Configuration.DATA_MANAGER_POOL_SIZE;
import static blockchain.config.Configuration.SAVE_MODE;
import static blockchain.config.Configuration.TYPE;
import static blockchain.model.BlockchainType.CRYPTOCURRENCY;
import static blockchain.model.BlockchainType.MESSENGER;
import static blockchain.utils.SystemUtils.pauseProcess;

public class Client {

    public static final ExecutorService blockchainExecutor = Executors.newFixedThreadPool(BLOCKCHAIN_POOL_SIZE);
    public static final ExecutorService dataManagerExecutor = Executors.newFixedThreadPool(DATA_MANAGER_POOL_SIZE);

    public static final Map<BlockchainType, DataManager> dataManagers = Map.of(
            MESSENGER, new MessageManager(),
            CRYPTOCURRENCY, new TransactionManager()
    );

    public static final DataManager dataManager = dataManagers.get(TYPE);

    public static void run() {
        Blockchain blockchain = new Blockchain();
        startBlockchain(blockchain);
        stopExecutors();
        saveBlockchain(blockchain);
    }

    private static void startBlockchain(Blockchain blockchain) {
        BlockFactory blockFactory = new BlockFactory(blockchain, dataManager);
        BlockchainService blockchainService;

        switch (TYPE) {
            case MESSENGER -> blockchainService =
                    new MessengerBlockchainService(blockchain, (MessageManager) dataManager, blockFactory);
            case CRYPTOCURRENCY -> blockchainService =
                    new CryptocurrencyBlockchainService(blockchain, (TransactionManager) dataManager, blockFactory);
            default -> throw new IllegalStateException("There is no such blockchain type");
        }
        startTasks(blockchain, blockchainService);
    }

    private static void startTasks(Blockchain blockchain, BlockchainService blockchainService) {
        while (!blockchain.reachedBlockLimit()) {
            blockchainExecutor.submit(blockchainService::increaseBlockchain);
            dataManagerExecutor.submit(dataManagers.get(TYPE)::generateData);
            pauseProcess(20);
        }
    }

    private static void stopExecutors() {
        blockchainExecutor.shutdownNow();
        dataManagerExecutor.shutdownNow();
    }

    private static void saveBlockchain(Blockchain blockchain) {
        if (SAVE_MODE) {
            SerializationUtils.saveBlockchainToFile(blockchain);
        }
    }
}
