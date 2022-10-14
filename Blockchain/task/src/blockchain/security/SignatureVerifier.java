package blockchain.security;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

import static blockchain.config.Configuration.SIGNATURE_ALGORITHM;

public final class SignatureVerifier {

    private SignatureVerifier() {
        throw new IllegalStateException();
    }

    public static boolean verifySignature(String text, byte[] signature, PublicKey publicKey) {
        Signature signatureVerifier;
        try {
            signatureVerifier = Signature.getInstance(SIGNATURE_ALGORITHM);
            signatureVerifier.initVerify(publicKey);
            signatureVerifier.update(text.getBytes());
            return signatureVerifier.verify(signature);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
            return false;
        }
    }
}
