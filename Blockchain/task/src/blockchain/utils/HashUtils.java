package blockchain.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import static blockchain.config.Configuration.HASH_ALGORITHM;

public final class HashUtils {

    private HashUtils() {
        throw new IllegalStateException();
    }

    public static String generateHash(String input){
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte elem: hash) {
                String hex = Integer.toHexString(0xff & elem);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
