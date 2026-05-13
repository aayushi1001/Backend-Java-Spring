package Threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VirtualThreadDemo {

    public static void main(String[] args) {
        // Old approach: thread pool with limited size
        ExecutorService pool = Executors.newFixedThreadPool(200);

        // New approach: one virtual thread per task, no artificial limit
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 100_000; i++) {
                executor.submit(() -> {
                    // Blocking DB call — totally fine with virtual threads
//                    String result = database.query("SELECT ...");
//                    processResult(result);
                });
            }
        }
    }
}
