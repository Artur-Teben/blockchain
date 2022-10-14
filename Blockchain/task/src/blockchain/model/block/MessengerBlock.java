package blockchain.model.block;

import blockchain.model.data.Message;

import java.util.List;

public class MessengerBlock extends Block {

    private final List<Message> messages;

    public MessengerBlock(long id, int minerNumber, long timestamp, String previousHash, long magicNumber,
                          String hash, long timeOfGenerating, List<Message> messages) {

        super(id, minerNumber, timestamp, previousHash, magicNumber, hash, timeOfGenerating);
        this.messages = messages;
    }

    @Override
    public List<Message> getData() {
        return messages;
    }
}
