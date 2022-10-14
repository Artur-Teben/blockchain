package blockchain.model;

import blockchain.model.block.Block;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import static blockchain.config.Configuration.BLOCKCHAIN_BLOCKS_LIMIT;
import static blockchain.config.Configuration.INITIAL_NUMBER_OF_SYMBOLS;

public class Blockchain implements Serializable {

    private static final long serialVersionUID = 2L;

    private volatile int numberOfSymbols = INITIAL_NUMBER_OF_SYMBOLS;
    private final List<Block> blocks = new CopyOnWriteArrayList<>();

    public int getNumberOfSymbols() {
        return numberOfSymbols;
    }

    public void setNumberOfSymbols(int numberOfSymbols) {
        this.numberOfSymbols = numberOfSymbols;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public Optional<Block> getLastBlock() {
        return blocks.isEmpty()
                ? Optional.empty()
                : Optional.of(blocks.get(blocks.size() - 1));
    }

    public boolean reachedBlockLimit() {
        return blocks.size() >= BLOCKCHAIN_BLOCKS_LIMIT;
    }
}
