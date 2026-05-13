package Locks;


public class SynchronizedCounter {
    private int count = 0;

    public synchronized void increment() {
        count++; // locks on 'this' (the Counter instance)
    }

    public synchronized int getCount() {
        return count; // also locks on 'this'
    }
}
