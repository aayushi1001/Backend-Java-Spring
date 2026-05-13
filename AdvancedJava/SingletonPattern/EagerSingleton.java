package SingletonPattern;

//TODO: Topic: Singleton Pattern Implementation with eager initialisation

/**
 * The problem is that the instance is created even if it is never used,
 * which wastes resources if the object is expensive to create.
 */

public class EagerSingleton {
    private static final EagerSingleton INSTANCE = new EagerSingleton();

    private EagerSingleton() {}

    public static EagerSingleton getInstance() {
        return INSTANCE;
    }
}
