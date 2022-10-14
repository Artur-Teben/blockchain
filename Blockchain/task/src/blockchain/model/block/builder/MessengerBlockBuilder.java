package blockchain.model.block.builder;

import blockchain.model.data.Message;
import blockchain.model.block.MessengerBlock;

import java.util.List;

public class MessengerBlockBuilder extends BlockBuilder {
    private final BlockBuilder blockBuilder;
    private List<Message> messages;

    public MessengerBlockBuilder(BlockBuilder blockBuilder) {
        this.blockBuilder = blockBuilder;
    }

    public MessengerBlockBuilder messages(List<Message> messages) {
        this.messages = messages;
        return this;
    }

    public MessengerBlock build() {
        return new MessengerBlock(
                blockBuilder.getId(), blockBuilder.getMinerNumber(), blockBuilder.getTimestamp(),
                blockBuilder.getPreviousHash(), blockBuilder.getMagicNumber(), blockBuilder.getHash(),
                blockBuilder.getTimeOfGenerating(), messages);
    }
}
