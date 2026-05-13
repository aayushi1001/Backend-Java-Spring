package SingletonPattern;

//TODO: Topic: Singleton Pattern Implementation with lazy initialisation

/**
 * Two threads can both see instance == null simultaneously
 * and both create a new object — singleton contract broken
 */

public class LazySingleton {
    private static LazySingleton instance;

    private LazySingleton() {}

    public static LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }
}
