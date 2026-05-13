package Threads;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolExecutorDemo {

    // Custom Thread Factory
    static class CustomThreadFactory implements ThreadFactory {
        private final String namePrefix;
        private final AtomicInteger threadCount = new AtomicInteger(1);

        CustomThreadFactory(String namePrefix) {
            this.namePrefix = namePrefix;
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName(namePrefix + "-thread-" + threadCount.getAndIncrement());
            thread.setDaemon(false);
            thread.setPriority(Thread.NORM_PRIORITY);
            System.out.println("[ThreadFactory] Created: " + thread.getName());
            return thread;
        }
    }

    // Custom Rejection Handler
    static class CustomRejectionHandler implements RejectedExecutionHandler {
        private final AtomicInteger rejectedCount = new AtomicInteger(0);

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            int count = rejectedCount.incrementAndGet();
            System.out.printf("[RejectionHandler] Task rejected! Total rejections: %d | " +
                            "Pool size: %d | Queue size: %d | Is shutdown: %b%n",
                    count,
                    executor.getPoolSize(),
                    executor.getQueue().size(),
                    executor.isShutdown()
            );
        }

        public int getRejectedCount() {
            return rejectedCount.get();
        }
    }

    static class SampleTask implements Runnable {
        private final int taskId;

        SampleTask(int taskId) {
            this.taskId = taskId;
        }

        @Override
        public void run() {
            System.out.printf("[Task-%02d] Started on %s%n", taskId, Thread.currentThread().getName());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.printf("[Task-%02d] Interrupted!%n", taskId);
                Thread.currentThread().interrupt();
            }
            System.out.printf("[Task-%02d] Finished on %s%n", taskId, Thread.currentThread().getName());
        }
    }


    public static void main(String[] args) throws InterruptedException {

        CustomThreadFactory threadFactory   = new CustomThreadFactory("worker-pool");
        CustomRejectionHandler rejectionHandler = new CustomRejectionHandler();

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2,                              // corePoolSize
                4,                              // maximumPoolSize
                30L, TimeUnit.SECONDS,          // keepAliveTime for threads above core size
                new LinkedBlockingQueue<>(4),   // bounded queue — capacity 4
                threadFactory,                  // our custom factory
                rejectionHandler                // our custom handler
        );

        System.out.println("═".repeat(55));
        System.out.println(" Submitting 12 tasks (core=2, max=4, queue=4)");
        System.out.println("═".repeat(55));

        // Submit 12 tasks:
        //   - 4 will run immediately (up to maximumPoolSize)
        //   - 4 will be queued
        //   - 4 will be rejected (queue + pool are full)
        for (int i = 1; i <= 12; i++) {
            System.out.println("[Main] Submitting Task-" + String.format("%02d", i));
            executor.submit(new SampleTask(i));
        }

        System.out.println("═".repeat(55));
        printStats(executor);
        System.out.println("═".repeat(55));

        // Graceful shutdown
        executor.shutdown();
        System.out.println("[Main] shutdown() called — waiting for tasks to finish...");

        if (executor.awaitTermination(30, TimeUnit.SECONDS)) {
            System.out.println("[Main] All tasks completed.");
        } else {
            System.out.println("[Main] Timeout reached — forcing shutdownNow().");
            executor.shutdownNow();
        }

        System.out.println("═".repeat(55));
        printStats(executor);
        System.out.printf("[Main] Total tasks rejected: %d%n", rejectionHandler.getRejectedCount());
        System.out.println("═".repeat(55));
    }

    static void printStats(ThreadPoolExecutor ex) {
        System.out.printf("""
                [Stats] Pool size        : %d
                [Stats] Active threads   : %d
                [Stats] Queue size       : %d
                [Stats] Tasks completed  : %d
                [Stats] Is shutdown      : %b
                [Stats] Is terminated    : %b%n""",
                ex.getPoolSize(),
                ex.getActiveCount(),
                ex.getQueue().size(),
                ex.getCompletedTaskCount(),
                ex.isShutdown(),
                ex.isTerminated()
        );
    }
}
