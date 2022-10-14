package blockchain.service.impl;

import blockchain.model.data.Data;
import blockchain.model.data.Transaction;
import blockchain.model.data.User;
import blockchain.service.DataManager;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static blockchain.config.Configuration.MAX_TRANSACTION_VALUE;
import static blockchain.config.Configuration.MIN_TRANSACTION_VALUE;
import static blockchain.config.Configuration.TRANSACTION_GENERATING_DELAY_IN_MILLIS;
import static blockchain.config.Configuration.USERS;
import static blockchain.utils.SystemUtils.getRandomNumber;
import static blockchain.utils.SystemUtils.pauseProcess;

public class TransactionManager implements DataManager {

    private static final List<Transaction> transactionsHistory = new ArrayList<>();

    @Override
    public List<Data> getData() {
        return transactionsHistory.stream()
                .map(data -> (Data) data)
                .toList();
    }

    @Override
    public void generateData() {
        int value = getRandomNumber(MIN_TRANSACTION_VALUE, MAX_TRANSACTION_VALUE);

//        synchronized (USERS) {
            User sender = getAppropriateUser(operationIsAllowed(value));
            User recipient = getAppropriateUser(isNotSameUser(sender));
            processTransaction(new Transaction(sender, recipient, value));
//        }
    }

    public void removeTransactionsFromHistory(List<Transaction> transactions) {
        transactionsHistory.removeAll(transactions);
    }

    private static Predicate<User> operationIsAllowed(int value) {
        return sender -> sender.getBalance() - value >= 0;
    }

    private static Predicate<User> isNotSameUser(User sender) {
        return recipient -> !recipient.equals(sender);
    }

    private static synchronized User getAppropriateUser(Predicate<User> userPredicate) {
        return USERS.stream()
                .filter(userPredicate)
                .toList().get(getRandomNumber(USERS.size() - 1));
    }

    private static synchronized void processTransaction(Transaction transaction) {
        User sender = transaction.getSender();
        User recipient = transaction.getRecipient();
        sender.setBalance(sender.getBalance() - transaction.getValue());
        recipient.setBalance(recipient.getBalance() + transaction.getValue());
        transactionsHistory.add(transaction);
    }
}
