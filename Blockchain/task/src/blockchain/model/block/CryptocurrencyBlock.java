package blockchain.model.block;

import blockchain.model.data.Transaction;

import java.util.List;

public class CryptocurrencyBlock extends Block {

    private final List<Transaction> transactions;

    public CryptocurrencyBlock(long id, int minerNumber, long timestamp, String previousHash, long magicNumber,
                               String hash, long timeOfGenerating, List<Transaction> transactions) {

        super(id, minerNumber, timestamp, previousHash, magicNumber, hash, timeOfGenerating);
        this.transactions = transactions;
    }

    @Override
    public List<Transaction> getData() {
        return transactions;
    }
}
