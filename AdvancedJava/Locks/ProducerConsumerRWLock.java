package Locks;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ProducerConsumerRWLock {

    private final Map<String, String> store = new HashMap<>();
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.ReadLock  readLock  = rwLock.readLock();
    private final ReentrantReadWriteLock.WriteLock writeLock = rwLock.writeLock();

    // Producer — acquires write lock, blocks all readers
    public void produce(String key, String value) {
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " [WRITE] Writing key=" + key);
            store.put(key, value);
            Thread.sleep(1000); // simulate write latency
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            writeLock.unlock();
        }
    }

    // Consumer — acquires read lock, concurrent with other readers
    public String consume(String key) {
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " [READ]  Reading key=" + key);
            Thread.sleep(1000); // simulate read latency
            return store.get(key);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        } finally {
            readLock.unlock();
        }
    }

    public static void main(String[] args) {
        ProducerConsumerRWLock cache = new ProducerConsumerRWLock();

        // One producer thread
        Thread producer = new Thread(() -> {
//            for (int i = 0; i < 5; i++) {
//                cache.produce("key" + i, "value" + i);
//            }
            cache.produce("first", "first");
        }, "Producer");

        Thread producer1 = new Thread(() -> {
            cache.produce("second", "second");
        }, "Producer1");

        // Multiple consumer threads
        Runnable consumerTask = () -> {
            String result = cache.consume("first");
            System.out.println(Thread.currentThread().getName() + " [GOT]   first => " + result);
        };

        Thread consumer1 = new Thread(consumerTask, "Consumer-1");
        Thread consumer2 = new Thread(consumerTask, "Consumer-2");
        Thread consumer3 = new Thread(consumerTask, "Consumer-3");

        consumer1.start();
        consumer2.start();
        consumer3.start();
        producer.start();
        producer1.start();

        // Give producer a head start, then unleash consumers
//        try {
//            Thread.sleep(300);
//        } catch (InterruptedException e) {}
//
//        consumer1.start();
//        consumer2.start();
//        consumer3.start();
    }
}
