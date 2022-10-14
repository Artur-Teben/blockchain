package blockchain.model.block.builder;

public class BlockBuilder {
    private long id;
    private int minerNumber;
    private long timestamp;
    private String previousHash;
    private long magicNumber;
    private String hash;
    private long timeOfGenerating;

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

    public BlockBuilder id(long id) {
        this.id = id;
        return this;
    }

    public BlockBuilder minerNumber(int minerNumber) {
        this.minerNumber = minerNumber;
        return this;
    }

    public BlockBuilder timestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public BlockBuilder previousHash(String previousHash) {
        this.previousHash = previousHash;
        return this;
    }

    public BlockBuilder magicNumber(long magicNumber) {
        this.magicNumber = magicNumber;
        return this;
    }

    public BlockBuilder hash(String hash) {
        this.hash = hash;
        return this;
    }

    public BlockBuilder timeOfGenerating(long timeOfGenerating) {
        this.timeOfGenerating = timeOfGenerating;
        return this;
    }

    public MessengerBlockBuilder toMessengerBlockBuilder() {
        return new MessengerBlockBuilder(this);
    }

    public CryptocurrencyBlockBuilder toCryptocurrencyBlockBuilder() {
        return new CryptocurrencyBlockBuilder(this);
    }
}
