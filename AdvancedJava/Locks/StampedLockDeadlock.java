package Locks;

import java.util.concurrent.locks.StampedLock;

public class StampedLockDeadlock {

    private static final StampedLock lock = new StampedLock();

    public static void outer() {
        long stamp = lock.readLock();  // acquires read lock
        System.out.println("Outer: acquired read lock");
        try {
            inner();  // calls inner, which tries to acquire another read lock
        } finally {
            lock.unlockRead(stamp);
        }
    }

    public static void inner() {
        System.out.println("Inner: trying to acquire read lock...");
        long stamp = lock.readLock();  // DEADLOCKS here — same thread, same lock
        System.out.println("Inner: acquired read lock (never reached)");
        try {
            // do something
        } finally {
            lock.unlockRead(stamp);
        }
    }

    public static void main(String[] args) {
        outer();  // hangs forever
    }
}
