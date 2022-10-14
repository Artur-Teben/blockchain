package blockchain.utils;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public final class SystemUtils {

    private SystemUtils() {
        throw new IllegalStateException();
    }

    public static void pauseProcess(long delay) {
        try {
            TimeUnit.MILLISECONDS.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static int getRandomNumber(int leftBound, int rightBound) {
        return ThreadLocalRandom.current().nextInt(leftBound, rightBound);
    }

    public static int getRandomNumber(int limit) {
        return ThreadLocalRandom.current().nextInt(0, limit);
    }

    public static int getRandomNumber() {
        return ThreadLocalRandom.current().nextInt();
    }
}
