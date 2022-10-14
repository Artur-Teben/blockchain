package blockchain.model.data;

import blockchain.security.KeyGenerator;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.concurrent.atomic.AtomicInteger;

import static blockchain.config.Configuration.SIGNATURE_ALGORITHM;
import static blockchain.config.Configuration.USER_INITIAL_BALANCE;

public class User {
    private final String name;
    private final AtomicInteger balance = new AtomicInteger();
    private final PrivateKey privateKey;
    private final PublicKey publicKey;

    public User(String name) {
        KeyGenerator keyGenerator = new KeyGenerator();
        keyGenerator.createKeys();
        this.name = name;
        this.privateKey = keyGenerator.getPrivateKey();
        this.publicKey = keyGenerator.getPublicKey();
        this.balance.set(USER_INITIAL_BALANCE);
    }

    public User(String name, int initialBalance) {
        this(name);
        this.balance.set(initialBalance);
    }

    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance.get();
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setBalance(int balance) {
        this.balance.set(balance);
    }

    public byte[] sign(String text) {
        Signature rsa;
        try {
            rsa = Signature.getInstance(SIGNATURE_ALGORITHM);
            rsa.initSign(privateKey);
            rsa.update(text.getBytes());
            return rsa.sign();
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;

        if (getBalance() != user.getBalance()) return false;
        if (!getName().equals(user.getName())) return false;
        if (!privateKey.equals(user.privateKey)) return false;
        return getPublicKey().equals(user.getPublicKey());
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getBalance();
        result = 31 * result + privateKey.hashCode();
        result = 31 * result + getPublicKey().hashCode();
        return result;
    }
}
