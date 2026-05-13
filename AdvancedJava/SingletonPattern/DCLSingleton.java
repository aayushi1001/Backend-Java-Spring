package SingletonPattern;

//TODO: Topic: Singleton Pattern Implementation with double checked synchronised initialisation

/**
 * The most sophisticated traditional approach — locks only during the first creation and not on every subsequent call.
 *
 * volatile is critical here — without it the JVM can reorder instructions
 * and a thread might see a partially constructed object
 */

public class DCLSingleton {

    private static volatile DCLSingleton instance;

    private DCLSingleton() {}

    public static DCLSingleton getInstance() {
        if (instance == null) {                     // first check — no lock
            synchronized (DCLSingleton.class) {
                if (instance == null) {             // second check — with lock
                    instance = new DCLSingleton();
                }
            }
        }
        return instance;
    }
}
