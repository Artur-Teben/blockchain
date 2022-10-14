package blockchain.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import static blockchain.config.Configuration.GENERATOR_KEY_LENGTH;
import static blockchain.config.Configuration.KEY_GENERATOR_ALGORITHM;

public class KeyGenerator {

    private final KeyPairGenerator keyPairGenerator;
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public KeyGenerator() {
        try {
            this.keyPairGenerator = KeyPairGenerator.getInstance(KEY_GENERATOR_ALGORITHM);
            this.keyPairGenerator.initialize(GENERATOR_KEY_LENGTH);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void createKeys() {
        KeyPair keyPair = this.keyPairGenerator.generateKeyPair();
        this.privateKey = keyPair.getPrivate();
        this.publicKey = keyPair.getPublic();
    }
}
