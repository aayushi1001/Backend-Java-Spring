package Threads;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class WorkStealingPoolDemo {

    // Below this size, fall back to Arrays.sort() — avoids fork overhead
    // for tiny arrays. Tune this based on your workload.
    private static final int THRESHOLD = 4096;

    static class MergeSortTask extends RecursiveAction {

        private final int[] arr;
        private final int[] temp;   // pre-allocated scratch buffer
        private final int lo;
        private final int hi;       // exclusive

        MergeSortTask(int[] arr, int[] temp, int lo, int hi) {
            this.arr  = arr;
            this.temp = temp;
            this.lo   = lo;
            this.hi   = hi;
        }

        @Override
        protected void compute() {
            int size = hi - lo;

            // Base case — hand off to sequential sort
            if (size <= THRESHOLD) {
                Arrays.sort(arr, lo, hi);
                return;
            }

            int mid = (lo + hi) >>> 1;  // unsigned shift avoids overflow

            MergeSortTask left  = new MergeSortTask(arr, temp, lo, mid);    // we call className not the method compute recursively
            MergeSortTask right = new MergeSortTask(arr, temp, mid, hi);

            // Fork left half onto own deque, compute right on this thread,
            // then join left. This keeps the current thread doing real work
            // rather than just waiting.
            left.fork();
            right.compute();
            left.join();

            merge(lo, mid, hi);
        }

        private void merge(int lo, int mid, int hi) {
            // Copy slice into temp buffer
            System.arraycopy(arr, lo, temp, lo, hi - lo);

            int i = lo;     // pointer into left half  (temp)
            int j = mid;    // pointer into right half (temp)
            int k = lo;     // pointer into output     (arr)

            while (i < mid && j < hi) {
                if (temp[i] <= temp[j]) {
                    arr[k++] = temp[i++];
                } else {
                    arr[k++] = temp[j++];
                }
            }

            // Copy any remaining elements from the left half.
            // Right half elements are already in place — no copy needed.
            while (i < mid) {
                arr[k++] = temp[i++];
            }
        }
    }

    public static void sort(int[] arr) {
        // Pre-allocate temp buffer once — avoids allocation inside recursion
        int[] temp = new int[arr.length];

        // Explicit pool instead of commonPool() — isolates this workload
        // and lets us control parallelism level
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());

        try {
            pool.invoke(new MergeSortTask(arr, temp, 0, arr.length));
        } finally {
            pool.shutdown();
        }
    }

    public static void main(String[] args) {
        int[] arr = {9, 3, 7, 1, 8, 2, 6, 4, 5, 0};
        System.out.println("Before: " + Arrays.toString(arr));

        sort(arr);

        System.out.println("After:  " + Arrays.toString(arr));
        // Before: [9, 3, 7, 1, 8, 2, 6, 4, 5, 0]
        // After:  [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
    }
}

