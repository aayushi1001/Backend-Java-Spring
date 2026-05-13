package Deadlock;

public class DeadlockDemo {

    private static final Object LOCK_A = new Object();
    private static final Object LOCK_B = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            synchronized (LOCK_A) {
                System.out.println("Thread 1: Holding Lock A, waiting for Lock B...");
                try { Thread.sleep(100); } catch (InterruptedException e) {}
                synchronized (LOCK_B) {
                    System.out.println("Thread 1: Acquired both locks!");
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (LOCK_B) {
                System.out.println("Thread 2: Holding Lock B, waiting for Lock A...");
                try { Thread.sleep(100); } catch (InterruptedException e) {}
                synchronized (LOCK_A) {
                    System.out.println("Thread 2: Acquired both locks!");
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}
