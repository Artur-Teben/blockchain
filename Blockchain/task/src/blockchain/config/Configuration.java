package blockchain.config;

import blockchain.model.BlockchainType;
import blockchain.model.data.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Configuration {

    // Executors
    public static final int BLOCKCHAIN_POOL_SIZE = 3;
    public static final int DATA_MANAGER_POOL_SIZE = 5;

    // Files
    public static final boolean SAVE_MODE = false;
    public static final String BLOCKCHAIN_DATABASE_FILE_NAME =
            "/Users/ateben/IdeaProjects/Blockchain/Blockchain/task/src/blockchain/resources/blockchain.txt";

    // Blockchain
    public static final BlockchainType TYPE = BlockchainType.CRYPTOCURRENCY;
    public static final String SYMBOL = "0";
    public static final int PRISE = 100;
    public static final int BLOCKCHAIN_BLOCKS_LIMIT = 15;
    public static final int INITIAL_NUMBER_OF_SYMBOLS = 0;
    public static final int MAX_NUMBER_OF_SYMBOLS = 4;
    public static final int MIN_NUMBER_OF_SYMBOLS = 0;
    public static final long MIN_TIME_OF_BLOCK_GENERATING = 5;
    public static final long MAX_TIME_OF_BLOCK_GENERATING = 10;

    // Data Managers
    public static final int MIN_TRANSACTION_VALUE = 1;
    public static final int MAX_TRANSACTION_VALUE = 100;
    public static final long TRANSACTION_GENERATING_DELAY_IN_MILLIS = 20;
    public static final long MESSAGE_GENERATING_DELAY_IN_MILLIS = 20;
    public static final List<String> RANDOM_TEXT = List.of(
            "Hey guys!", "Wow, cool chat", "How did you find this chat?", "This chat is so popular", "What's up?",
            "My first message here", "Nice to see you folks", "Where are you from?",
            "I definitely need to invite my friends", "I need to go, bye!");

    // Security
    public static final int GENERATOR_KEY_LENGTH = 1024;
    public static final String SIGNATURE_ALGORITHM = "SHA1withRSA";
    public static final String KEY_GENERATOR_ALGORITHM = "RSA";
    public static final String HASH_ALGORITHM = "SHA-256";

    // Users
    public static final String DEFAULT_THREAD_NAME = "pool";
    public static final int USER_INITIAL_BALANCE = 100;
    public static final List<User> USERS = new CopyOnWriteArrayList<>(){
        {
            add(new User("Kate"));
            add(new User("Liam"));
            add(new User("Noah"));
            add(new User("FastFood"));
            add(new User("James"));
            add(new User("William"));
            add(new User("Lucas"));
            add(new User("CarShop"));
            add(new User("GamingShop"));
        }
    };
}
