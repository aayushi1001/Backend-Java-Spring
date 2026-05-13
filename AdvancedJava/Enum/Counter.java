package Enum;


import java.util.concurrent.atomic.AtomicInteger;

/**
 * The thread safety guarantee only applies to the creation of enum constants.
 * If your enum holds mutable state, that state is not thread-safe and needs to be protected manually.
 */

public enum Counter {
    INSTANCE;

    private int count = 0; // mutable state — NOT thread-safe!

    public void increment() {
        count++; // race condition if multiple threads call this
    }

    public int getCount() {
        return count;
    }
}

//The fix is to use AtomicInteger:
enum Counter1 {
    INSTANCE;

    private final AtomicInteger count = new AtomicInteger(0);

    public void increment() {
        count.incrementAndGet(); // atomic — thread-safe
    }

    public int getCount() {
        return count.get();
    }
}
