package blockchain.model.data;

import java.security.PublicKey;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public class Transaction implements Data {

    private static final long serialVersionUID = 1L;
    private static final AtomicLong nextSerialMessageId = new AtomicLong(1);

    private final long id;
    private final User sender;
    private final User recipient;
    private final String transaction;
    private final int value;
    private final byte[] signature;
    private final PublicKey publicKey;
    private final long timestamp;

    public Transaction(User sender, User recipient, int value) {
        this.id = nextSerialMessageId.getAndIncrement();
        this.sender = sender;
        this.recipient = recipient;
        this.transaction = this.sender.getName() + " sent " + value + " VC to " + this.recipient.getName();
        this.value = value;
        this.signature = sender.sign(transaction);
        this.publicKey = sender.getPublicKey();
        this.timestamp = new Date().getTime();
    }

    @Override
    public long getId() {
        return id;
    }

    public User getSender() {
        return sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public String getTransaction() {
        return transaction;
    }

    public int getValue() {
        return value;
    }

    @Override
    public byte[] getSignature() {
        return signature;
    }

    @Override
    public PublicKey getPublicKey() {
        return publicKey;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return transaction;
    }
}
