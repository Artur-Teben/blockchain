package blockchain.model.block.factory;

import blockchain.model.block.Block;
import blockchain.model.Blockchain;
import blockchain.model.data.Data;
import blockchain.model.data.Message;
import blockchain.model.data.Transaction;
import blockchain.model.block.builder.BlockBuilder;
import blockchain.service.DataManager;
import blockchain.utils.HashUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

import static blockchain.config.Configuration.DEFAULT_THREAD_NAME;
import static blockchain.config.Configuration.SYMBOL;
import static blockchain.config.Configuration.TYPE;

public class BlockFactory {

    private static final AtomicLong nextSerialMinerNumber = new AtomicLong(1);

    private final Blockchain blockchain;
    private final DataManager dataManager;

    public BlockFactory(Blockchain blockchain, DataManager dataManager) {
        this.blockchain = blockchain;
        this.dataManager = dataManager;
    }

    public synchronized long generateSerialMinerNumber() {
        return nextSerialMinerNumber.getAndIncrement();
    }

    public Optional<Block> generateBlock() {
        long blockTimestamp = new Date().getTime();
        long blockId = getBlockId();
        List<?> data = getData(blockTimestamp, blockId);

//        if (blockId != 1 && data.isEmpty()) {
//            return Optional.empty();
//        }

        String previousHash = getPreviousHash();
        String hash;
        long magicNumber;
        int minerNumber = getMinerNumber();

        do {
            if (blockchain.reachedBlockLimit()) {
                return Optional.empty();
            }
            magicNumber = ThreadLocalRandom.current().nextInt();
            hash = HashUtils.generateHash(blockId + minerNumber + blockTimestamp + previousHash + magicNumber + data);
        } while (!hash.startsWith(SYMBOL.repeat(blockchain.getNumberOfSymbols())));

        return buildBlock(data, new BlockBuilder()
                .id(blockId)
                .minerNumber(minerNumber)
                .timestamp(blockTimestamp)
                .previousHash(previousHash)
                .magicNumber(magicNumber)
                .hash(hash)
                .timeOfGenerating((new Date().getTime() - blockTimestamp) / 1000));
    }

    private static Optional<Block> buildBlock(List<?> data, BlockBuilder blockBuilder) {
        return switch (TYPE) {
            case MESSENGER -> Optional.of(blockBuilder.toMessengerBlockBuilder()
                    .messages(data.stream()
                            .map(element -> (Message) element)
                            .toList())
                    .build());
            case CRYPTOCURRENCY -> Optional.of(blockBuilder.toCryptocurrencyBlockBuilder()
                    .transactions(data.stream()
                            .map(element -> (Transaction) element)
                            .toList())
                    .build());
        };
    }

    private String getPreviousHash() {
        return blockchain.getLastBlock().isPresent()
                ? blockchain.getLastBlock().get().getHash()
                : SYMBOL;
    }

    private List<?> getData(long blockTimestamp, long blockId) {
        List<Data> data = getValidBlockData(blockTimestamp, blockId);

        return switch (TYPE) {
            case MESSENGER -> mapToMessages(data);
            case CRYPTOCURRENCY -> mapToTransactions(data);
        };
    }

    private static List<Transaction> mapToTransactions(List<Data> data) {
        return data.stream()
                .map(element -> (Transaction) element)
                .toList();
    }

    private static List<Message> mapToMessages(List<Data> data) {
        return data.stream()
                .map(element -> (Message) element)
                .toList();
    }

    private long getBlockId() {
        return blockchain.getLastBlock().isPresent()
                ? blockchain.getLastBlock().get().getId() + 1
                : 1;
    }

    private List<Data> getValidBlockData(long blockTimestamp, long id) {
        return (id != 1)
                ? dataManager.getData().stream()
                    .filter(data -> data.getTimestamp() < blockTimestamp)
                    .toList()
                : new ArrayList<>();
    }

    private int getMinerNumber() {
        String minerNumber = Thread.currentThread().getName();

        if (minerNumber.contains(DEFAULT_THREAD_NAME)) {
            minerNumber = String.valueOf(generateSerialMinerNumber());
            Thread.currentThread().setName(minerNumber);
        }
        return Integer.parseInt(minerNumber);
    }
}
