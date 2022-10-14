package blockchain.model.block.builder;

import blockchain.model.block.CryptocurrencyBlock;
import blockchain.model.data.Transaction;

import java.util.List;

public class CryptocurrencyBlockBuilder {
    private final BlockBuilder blockBuilder;
    private List<Transaction> transactions;

    public CryptocurrencyBlockBuilder(BlockBuilder blockBuilder) {
        this.blockBuilder = blockBuilder;
    }

    public CryptocurrencyBlockBuilder transactions(List<Transaction> transactions) {
        this.transactions = transactions;
        return this;
    }

    public CryptocurrencyBlock build() {
        return new CryptocurrencyBlock(
                blockBuilder.getId(), blockBuilder.getMinerNumber(), blockBuilder.getTimestamp(),
                blockBuilder.getPreviousHash(), blockBuilder.getMagicNumber(), blockBuilder.getHash(),
                blockBuilder.getTimeOfGenerating(), transactions);
    }
}
