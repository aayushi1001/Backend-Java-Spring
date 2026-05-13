package Locks;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The key advantage here is having two separate conditions on the same lock — notFull and notEmpty.
 * With synchronized + notifyAll(), you'd wake up all waiting threads (both producers and consumers)
 * even when only one type needs to act. With separate Condition objects, you signal only the relevant threads.
 **/

public class LockCondition<T> {

    private final Queue<T> queue = new LinkedList<>();
    private final int capacity;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notFull  = lock.newCondition(); // wait when full
    private final Condition notEmpty = lock.newCondition(); // wait when empty

    public LockCondition(int capacity) {
        this.capacity = capacity;
    }

    public void put(T item) throws InterruptedException {
        lock.lock();
        try {
            while (queue.size() == capacity) {
                notFull.await();         // wait until space is available
            }
            queue.add(item);
            notEmpty.signal();           // tell consumers an item is ready
        } finally {
            lock.unlock();
        }
    }

    ExecutorService a = Executors.newFixedThreadPool(4);

    public T take() throws InterruptedException {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                notEmpty.await();        // wait until an item is available
            }
            T item = queue.poll();
            notFull.signal();            // tell producers space is available
            return item;
        } finally {
            lock.unlock();
        }
    }
}
