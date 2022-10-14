package blockchain.model.data;

import java.io.Serializable;
import java.security.PublicKey;

public interface Data extends Serializable {

    long getId();

    byte[] getSignature();

    PublicKey getPublicKey();

    long getTimestamp();
}
