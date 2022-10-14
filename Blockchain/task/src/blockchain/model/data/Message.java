package blockchain.model.data;

import java.security.PublicKey;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public class Message implements Data {

    private static final long serialVersionUID = 1L;
    private static final AtomicLong nextSerialMessageId = new AtomicLong(1);

    private final long id;
    private final String userName;
    private final String text;
    private final byte[] signature;
    private final PublicKey publicKey;
    private final long timestamp;

    public Message(User user, String text) {
        this.id = nextSerialMessageId.getAndIncrement();
        this.userName = user.getName();
        this.text = text;
        this.signature = user.sign(text);
        this.publicKey = user.getPublicKey();
        this.timestamp = new Date().getTime();
    }

    public long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getText() {
        return text;
    }

    public byte[] getSignature() {
        return signature;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return userName + ": " + text;
    }
}
