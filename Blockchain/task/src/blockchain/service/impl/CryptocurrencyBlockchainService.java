package blockchain.service.impl;

import blockchain.model.Blockchain;
import blockchain.model.block.Block;
import blockchain.model.block.CryptocurrencyBlock;
import blockchain.model.data.Data;
import blockchain.model.data.Transaction;
import blockchain.model.data.User;
import blockchain.model.block.factory.BlockFactory;
import blockchain.service.BlockchainService;

import java.util.List;
import java.util.Optional;

import static blockchain.config.Configuration.PRISE;
import static blockchain.config.Configuration.SYMBOL;
import static blockchain.config.Configuration.USERS;
import static blockchain.security.SignatureVerifier.verifySignature;

public class CryptocurrencyBlockchainService implements BlockchainService {

    private final Blockchain blockchain;
    private final TransactionManager transactionManager;
    private final BlockFactory blockFactory;

    public CryptocurrencyBlockchainService(Blockchain blockchain, TransactionManager transactionManager, BlockFactory blockFactory) {
        this.blockchain = blockchain;
        this.transactionManager = transactionManager;
        this.blockFactory = blockFactory;
    }

    @Override
    public void increaseBlockchain() {
        Optional<Block> optionalBlock = blockFactory.generateBlock();
        List<Block> blocks = blockchain.getBlocks();

        if (optionalBlock.isPresent()) {
            CryptocurrencyBlock cryptocurrencyBlock = (CryptocurrencyBlock) optionalBlock.get();

            synchronized (blockchain) {
                if (!blockchain.reachedBlockLimit() && verifyBlock(cryptocurrencyBlock)) {
                    rewardMiner(cryptocurrencyBlock.getMinerNumber());
                    blocks.add(cryptocurrencyBlock);
                    Printer.printBlock(cryptocurrencyBlock, blockchain);
                    transactionManager.removeTransactionsFromHistory(cryptocurrencyBlock.getData());
                }
            }
        }
    }

    private void rewardMiner(int minerNumber) {
        String minerName = String.format("miner%s", minerNumber);

        if (USERS.stream()
                .map(User::getName)
                .noneMatch(userName -> userName.equals(minerName))) {

            USERS.add(new User(minerName, PRISE));
        } else {
            User miner = USERS.stream()
                    .filter(user -> user.getName().equals(minerName))
                    .findFirst().orElse(new User(minerName));
            miner.setBalance(miner.getBalance() + PRISE);
        }
    }

    private boolean verifyBlock(CryptocurrencyBlock block) {
        return blockchain.getLastBlock()
                .map(lastCryptocurrencyBlock -> lastCryptocurrencyBlock.getHash().equals(block.getPreviousHash())
                        && verifyHashStartsWithNumberOfSymbols(block.getHash())
                        && verifyTransactions(block, (CryptocurrencyBlock) lastCryptocurrencyBlock))
                .orElse(verifyHashStartsWithNumberOfSymbols(block.getHash()));
    }

    private boolean verifyHashStartsWithNumberOfSymbols(String hash) {
        return hash.startsWith(SYMBOL.repeat(blockchain.getNumberOfSymbols()));
    }

    private boolean verifyTransactions(CryptocurrencyBlock block, CryptocurrencyBlock lastCryptocurrencyBlock) {
        return verifySignatures(block.getData()) && verifyTransactionsIds(block.getData(), lastCryptocurrencyBlock);
    }

    private boolean verifyTransactionsIds(List<Transaction> transactions, CryptocurrencyBlock lastCryptocurrencyBlock) {
        List<Transaction> lastCryptocurrencyBlockTransactions = lastCryptocurrencyBlock.getData();

        if (lastCryptocurrencyBlockTransactions.isEmpty()) {
            return true;
        }

        return transactions.stream()
                .map(Data::getId)
                .allMatch(id -> id > lastCryptocurrencyBlockTransactions.get(lastCryptocurrencyBlockTransactions.size() - 1).getId());
    }

    private boolean verifySignatures(List<Transaction> transactions) {
        return transactions.stream()
                .allMatch(transaction -> verifySignature(transaction.getTransaction(), transaction.getSignature(), transaction.getPublicKey()));
    }
}
