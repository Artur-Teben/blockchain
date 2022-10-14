package blockchain.model.block;

import java.io.Serializable;
import java.util.List;

public abstract class Block implements Serializable {

    private static final long serialVersionUID = 1L;
    private final long id;
    private final int minerNumber;
    private final long timestamp;
    private final String previousHash;
    private final long magicNumber;
    private final String hash;
    private final long timeOfGenerating;

    public Block(long id, int minerNumber, long timestamp, String previousHash, long magicNumber,
                 String hash, long timeOfGenerating) {
        this.id = id;
        this.minerNumber = minerNumber;
        this.timestamp = timestamp;
        this.previousHash = previousHash;
        this.magicNumber = magicNumber;
        this.hash = hash;
        this.timeOfGenerating = timeOfGenerating;
    }

    public abstract List<?> getData();

    public long getId() {
        return id;
    }

    public int getMinerNumber() {
        return minerNumber;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public long getMagicNumber() {
        return magicNumber;
    }

    public String getHash() {
        return hash;
    }

    public long getTimeOfGenerating() {
        return timeOfGenerating;
    }
}
