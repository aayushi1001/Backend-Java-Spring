package Locks;

import java.util.concurrent.locks.StampedLock;

public class StampedLockExample {
    public static void main(String[] args) {
        int sharedValue = 10;
        StampedLock lock = new StampedLock();

        //Optimistic Read
        long stamp = lock.tryOptimisticRead();
        int value = sharedValue; // read without locking

        if (!lock.validate(stamp)) {
            // write occurred — fall back to a real read lock
            stamp = lock.readLock();
            try {
                value = sharedValue;
            } finally {
                lock.unlockRead(stamp);
            }
        }


        //Pessimistic Read
        long stamp1 = lock.readLock();
        try {
            // read shared state
        } finally {
            lock.unlockRead(stamp1);
        }


        //Write Lock
        long stamp2 = lock.writeLock();
        try {
            // modify shared state
        } finally {
            lock.unlockWrite(stamp2);
        }


        //Lock Conversion: Upgrade read → write
        long stamp4 = lock.readLock();
        try {
            long writeStamp = lock.tryConvertToWriteLock(stamp4);
            if (writeStamp != 0L) {
                stamp4 = writeStamp;
                // now have the write lock
            } else {
                lock.unlockRead(stamp4);
                stamp4 = lock.writeLock(); // full write lock
            }
        } finally {
            lock.unlock(stamp4);
        }
    }
}
