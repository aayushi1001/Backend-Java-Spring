package Threads;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorDemo {
    public static void main(String[] args) {

        // Creating an instance - two ways
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4); // 4 core threads
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(4);

        // Run once after a delay
        scheduler.schedule(() -> {
            System.out.println("Runs after 5 seconds");
        }, 5, TimeUnit.SECONDS);


        // Runs immediately at first and then after a delay of 10 sec
        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("Runs every 10 seconds");
        }, 0, 10, TimeUnit.SECONDS);

        scheduler.scheduleWithFixedDelay(() -> {
            System.out.println("Runs 3 seconds after the previous run completes");
        }, 0, 3, TimeUnit.SECONDS);

        scheduler.shutdown();
        executor.shutdown();
    }
}
