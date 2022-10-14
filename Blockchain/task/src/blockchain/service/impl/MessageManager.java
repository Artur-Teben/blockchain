package blockchain.service.impl;

import blockchain.model.data.Data;
import blockchain.model.data.Message;
import blockchain.model.data.User;
import blockchain.service.DataManager;

import java.util.ArrayList;
import java.util.List;

import static blockchain.config.Configuration.USERS;
import static blockchain.config.Configuration.MESSAGE_GENERATING_DELAY_IN_MILLIS;
import static blockchain.config.Configuration.RANDOM_TEXT;
import static blockchain.utils.SystemUtils.getRandomNumber;
import static blockchain.utils.SystemUtils.pauseProcess;

public class MessageManager implements DataManager {

    private final List<Message> messagesHistory = new ArrayList<>();

    @Override
    public List<Data> getData() {
        return messagesHistory.stream()
                .map(data -> (Data) data)
                .toList();
    }

    @Override
    public void generateData() {
        User user = USERS.get(getRandomNumber(USERS.size()));
        String text = RANDOM_TEXT.get(getRandomNumber(RANDOM_TEXT.size()));
        messagesHistory.add(new Message(user, text));
//        pauseProcess(MESSAGE_GENERATING_DELAY_IN_MILLIS);
    }

    public void removeMessagesFromHistory(List<Message> messages) {
        messagesHistory.removeAll(messages);
    }
}
