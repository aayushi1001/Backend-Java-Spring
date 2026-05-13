package Locks;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class LockInterrupt {

    public static void main(String[] args) {
        AtomicInteger a = new AtomicInteger();
        a.addAndGet(10);
        ReentrantLock lock = new ReentrantLock();

        Thread t = new Thread(() -> {
            try {
                lock.lockInterruptibly(); // waits, but can be cancelled
                try {
                    // do work
                } finally {
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                System.out.println("Thread was interrupted while waiting for lock");
                // clean up, exit gracefully — no lock was acquired
            }
        });

        t.start();
        t.interrupt(); // cancels the waiting thread
    }

}


