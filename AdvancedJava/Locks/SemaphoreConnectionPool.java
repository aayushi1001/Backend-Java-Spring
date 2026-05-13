package Locks;

import java.util.concurrent.Semaphore;

public class SemaphoreConnectionPool {

    private final Semaphore semaphore;
    private final String[] connections;
    private final boolean[] inUse;

    public SemaphoreConnectionPool(int size) {
        this.semaphore = new Semaphore(size);
        this.connections = new String[size]; // pretend these are real connections
        this.inUse = new boolean[size];
        for (int i = 0; i < size; i++) {
            connections[i] = "Connection-" + i;
        }
    }

    public String acquire() throws InterruptedException {
        semaphore.acquire(); // block until a connection is free
        return getAvailableConnection();
    }

    public void release(String connection) {
        markAsAvailable(connection);
        semaphore.release(); // return the permit
    }

    private synchronized String getAvailableConnection() {
        for (int i = 0; i < connections.length; i++) {
            if (!inUse[i]) {
                inUse[i] = true;
                return connections[i];
            }
        }
        return null; // won't happen due to semaphore
    }

    private synchronized void markAsAvailable(String connection) {
        for (int i = 0; i < connections.length; i++) {
            if (connections[i].equals(connection)) {
                inUse[i] = false;
                break;
            }
        }
    }
}
