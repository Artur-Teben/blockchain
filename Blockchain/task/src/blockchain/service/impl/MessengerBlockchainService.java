package blockchain.service.impl;

import blockchain.model.Blockchain;
import blockchain.model.block.Block;
import blockchain.model.data.Data;
import blockchain.model.data.Message;
import blockchain.model.block.MessengerBlock;
import blockchain.model.block.factory.BlockFactory;
import blockchain.service.BlockchainService;

import java.util.List;
import java.util.Optional;

import static blockchain.config.Configuration.SYMBOL;
import static blockchain.security.SignatureVerifier.verifySignature;

public class MessengerBlockchainService implements BlockchainService {

    private final Blockchain blockchain;
    private final MessageManager messageManager;
    private final BlockFactory blockFactory;

    public MessengerBlockchainService(Blockchain blockchain, MessageManager messageManager, BlockFactory blockFactory) {
        this.blockchain = blockchain;
        this.messageManager = messageManager;
        this.blockFactory = blockFactory;
    }

    @Override
    public void increaseBlockchain() {
        Optional<Block> optionalBlock = blockFactory.generateBlock();
        List<Block> blocks = blockchain.getBlocks();

        if (optionalBlock.isPresent()) {
            MessengerBlock messengerBlock = (MessengerBlock) optionalBlock.get();

            synchronized (blockchain) {
                if (!blockchain.reachedBlockLimit() && verifyBlock(messengerBlock)) {
                    blocks.add(messengerBlock);
                    Printer.printBlock(messengerBlock, blockchain);
                    messageManager.removeMessagesFromHistory(messengerBlock.getData());
                }
            }
        }
    }

    private boolean verifyBlock(MessengerBlock block) {
        return blockchain.getLastBlock()
                .map(lastMessengerBlock -> lastMessengerBlock.getHash().equals(block.getPreviousHash())
                        && verifyHashStartsWithNumberOfSymbols(block.getHash())
                        && verifyMessages(block, (MessengerBlock) lastMessengerBlock))
                .orElse(verifyHashStartsWithNumberOfSymbols(block.getHash()));
    }

    private boolean verifyHashStartsWithNumberOfSymbols(String hash) {
        return hash.startsWith(SYMBOL.repeat(blockchain.getNumberOfSymbols()));
    }

    private boolean verifyMessages(MessengerBlock block, MessengerBlock lastMessengerBlock) {
        return verifySignatures(block.getData()) && verifyMessageIds(block.getData(), lastMessengerBlock);
    }

    private boolean verifyMessageIds(List<Message> messages, MessengerBlock lastMessengerBlock) {
        List<Message> lastMessengerBlockMessages = lastMessengerBlock.getData();

        if (lastMessengerBlockMessages.isEmpty()) {
            return true;
        }

        return messages.stream()
                .map(Data::getId)
                .allMatch(id -> id > lastMessengerBlockMessages.get(lastMessengerBlockMessages.size() - 1).getId());
    }

    private boolean verifySignatures(List<Message> messages) {
        return messages.stream()
                .allMatch(message -> verifySignature(message.getText(), message.getSignature(), message.getPublicKey()));
    }
}
