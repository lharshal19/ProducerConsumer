

import java.util.concurrent.atomic.AtomicInteger;

class ProducerConsumerAppTest {
    public static void testSuccessScenario() {
        MessageQueue queue = new MessageQueue();
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger errorCount = new AtomicInteger(0);

        Thread producerThread = new Thread(new Producer(queue));
        Thread consumerThread = new Thread(new Consumer(queue, successCount, errorCount));

        producerThread.start();
        consumerThread.start();

        try {
            producerThread.join();
            consumerThread.interrupt();
            consumerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        assert successCount.get() > 0 : "Success count should be greater than 0";
        assert errorCount.get() == 0 : "Error count should be 0";
    }
    public static void testFailureScenario() {
        MessageQueue queue = new MessageQueue();
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger errorCount = new AtomicInteger(0);

        Thread consumerThread = new Thread(new Consumer(queue, successCount, errorCount));
        consumerThread.start();

        try {
            Thread.sleep(1000); // Let the consumer wait
            consumerThread.interrupt();
            consumerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        assert successCount.get() == 0 : "Success count should be 0";
        assert errorCount.get() > 0 : "Error count should be greater than 0";
    }

    public static void main(String[] args) {
        testSuccessScenario();
        testFailureScenario();
        System.out.println("All tests passed.");
    }
}
