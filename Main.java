/*
    Anchen Kruger, u25073703
    Caleb Jennings, u25173805
    Chloe Larsen, u25004141
*/
public class Main {

    private static final boolean DEBUG_PRINT = false;

    public static void main(String[] args) throws InterruptedException {

        int operationsPerThread = 1000;
        int[] threadCounts = {2, 4, 8, 16};

        System.out.printf("%-8s %-20s %-20s%n",
                "Threads", "Coarse-Grained (ms)", "Fine-Grained (ms)");
        System.out.println("--------------------------------------------------");

        for (int threads : threadCounts) {

            long coarse = runExperiment(new CoarseList(), threads, operationsPerThread);
            long fine   = runExperiment(new FineList(),   threads, operationsPerThread);

            System.out.printf("%-8d %-20d %-20d%n", threads, coarse, fine);
        }
    }

    private static long runExperiment(Object list, int numberOfThreads, int operationsPerThread)
            throws InterruptedException {

        Thread[] threads = new Thread[numberOfThreads];

        long startTime = System.nanoTime();

        for (int i = 0; i < numberOfThreads; i++) {

            final int threadID = i;

            threads[i] = new Thread(() -> {
                for (int j = 0; j < operationsPerThread; j++) {
                    int value = (threadID * 1000) + (j % 1000);

                    if (j % 3 == 0) {
                        boolean success = add(list, value);
                        if (DEBUG_PRINT)
                            System.out.println(Thread.currentThread().threadId()
                                    + " | Adding: " + value + ", " + success);
                    } else if (j % 3 == 1) {
                        boolean contains = contains(list, value);
                        if (DEBUG_PRINT)
                            System.out.println(Thread.currentThread().threadId()
                                    + " | Contains: " + value + ", " + contains);
                    } else {
                        boolean success = remove(list, value);
                        if (DEBUG_PRINT)
                            System.out.println(Thread.currentThread().threadId()
                                    + " | Removing: " + value + ", " + success);
                    }
                }
            });

            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        long endTime = System.nanoTime();

        return (endTime - startTime) / 1_000_000; // ns to ms
    }


    private static boolean add(Object list, int value) {
        if (list instanceof CoarseList) return ((CoarseList) list).add(value);
        return ((FineList) list).add(value);
    }

    private static boolean remove(Object list, int value) {
        if (list instanceof CoarseList) return ((CoarseList) list).remove(value);
        return ((FineList) list).remove(value);
    }

    private static boolean contains(Object list, int value) {
        if (list instanceof CoarseList) return ((CoarseList) list).contains(value);
        return ((FineList) list).contains(value);
    }
}