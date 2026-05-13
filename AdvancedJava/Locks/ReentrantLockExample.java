package Locks;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockExample {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();

        lock.lock();
        try {
            // critical section — exclusive access
        } finally {
            lock.unlock();
        }
    }
}
