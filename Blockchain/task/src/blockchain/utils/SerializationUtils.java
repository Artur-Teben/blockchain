package blockchain.utils;

import blockchain.model.Blockchain;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static blockchain.config.Configuration.BLOCKCHAIN_DATABASE_FILE_NAME;

public final class SerializationUtils {

    private SerializationUtils() {
        throw new IllegalStateException();
    }

    public static void saveBlockchainToFile(Blockchain blockchain) {
        try (FileOutputStream fos = new FileOutputStream(BLOCKCHAIN_DATABASE_FILE_NAME);
             BufferedOutputStream bos = new BufferedOutputStream(fos);
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(blockchain.getBlocks());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static Object loadBlockchainFromFile() {
        try (FileInputStream fis = new FileInputStream(BLOCKCHAIN_DATABASE_FILE_NAME);
             BufferedInputStream bis = new BufferedInputStream(fis);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            return ois.readObject();
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
