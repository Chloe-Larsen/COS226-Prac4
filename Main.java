public class Main {

    private static final boolean DEBUG_PRINT = true;

    public static void main(String[] args) throws InterruptedException {

        // CoarseList list = new CoarseList();
         FineList list = new FineList();

        int numberOfThreads = 2;
        int operationsPerThread = 1000;

        Thread[] threads = new Thread[numberOfThreads];

        long startTime = System.nanoTime();

        for (int i = 0; i < numberOfThreads; i++) {

            final int threadID = i;

            threads[i] = new Thread(() -> {
                for (int j = 0; j < operationsPerThread; j++) {
                    int value = (threadID * 1000) + (j % 1000);

                    if (j % 3 == 0) {
                        boolean success = list.add(value);
                        if (DEBUG_PRINT)
                            System.out.println(
                                    Thread.currentThread().threadId() + " | Adding: " + value + ", " + success);
                    } else if (j % 3 == 1) {
                        boolean contains = list.contains(value);
                        if (DEBUG_PRINT)
                            System.out.println(
                                    Thread.currentThread().threadId() + " | Contains: " + value + ", " + contains);
                    } else {
                        boolean success = list.remove(value);
                        if (DEBUG_PRINT)
                            System.out.println(
                                    Thread.currentThread().threadId() + " | Removing: " + value + ", " + success);
                    }
                }
            });

            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        long endTime = System.nanoTime();

        double executionTime = (endTime - startTime) / 1000000;
        System.out.println("Execution time: " + executionTime + " ms");
    }
}