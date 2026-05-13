package Locks;

import java.util.LinkedList;
import java.util.Queue;

class ProducerConsumerITC {
    private final Queue<Integer> queue = new LinkedList<>();
    private final int MAX_SIZE = 5;

    public synchronized void produce(int item) throws InterruptedException {
        while (queue.size() == MAX_SIZE) {
            System.out.println("Queue full, producer waiting...");
            wait();
        }
        queue.add(item);
        System.out.println("Produced: " + item);
        notify();
    }

    public synchronized int consume() throws InterruptedException {
        while (queue.isEmpty()) {
            System.out.println("Queue empty, consumer waiting...");
            wait();
        }
        int item = queue.poll();
        System.out.println("Consumed: " + item);
        notify();
        return item;
    }
}

// Producer Thread
class Producer implements Runnable {
    private final ProducerConsumerITC pc;

    public Producer(ProducerConsumerITC pc) {
        this.pc = pc;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            try {
                pc.produce(i);
                Thread.sleep(500); // Simulate time to produce
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Producer interrupted");
            }
        }
        System.out.println("Producer finished.");
    }
}

// Consumer Thread
class Consumer implements Runnable {
    private final ProducerConsumerITC pc;

    public Consumer(ProducerConsumerITC pc) {
        this.pc = pc;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            try {
                pc.consume();
                Thread.sleep(800); // Simulate time to consume (slower than producer)
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Consumer interrupted");
            }
        }
        System.out.println("Consumer finished.");
    }
}

// Main Class
class Main {
    public static void main(String[] args) throws InterruptedException {
        ProducerConsumerITC pc = new ProducerConsumerITC();

        Thread producerThread = new Thread(new Producer(pc), "ProducerThread");
        Thread consumerThread = new Thread(new Consumer(pc), "ConsumerThread");

        producerThread.start();
        consumerThread.start();

        // Wait for both threads to finish
        producerThread.join();
        consumerThread.join();

        System.out.println("All tasks completed.");
    }
}
