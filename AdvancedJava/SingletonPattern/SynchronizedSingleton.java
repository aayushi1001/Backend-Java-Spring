package SingletonPattern;

//TODO: Topic: Singleton Pattern Implementation with synchronised initialisation

/**
 * Thread-safe but performance bottleneck
 * lock is acquired on every call, even after instance is created
 */

public class SynchronizedSingleton {
    private static SynchronizedSingleton instance;

    private SynchronizedSingleton() {}

    public static synchronized SynchronizedSingleton getInstance() {
        if (instance == null) {
            instance = new SynchronizedSingleton();
        }
        return instance;
    }
}
