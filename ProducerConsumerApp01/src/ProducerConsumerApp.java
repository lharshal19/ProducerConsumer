

import java.util.concurrent.atomic.AtomicInteger;

// Main application class
public class ProducerConsumerApp {
    public static void main(String[] args) {
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

        System.out.println("Total Messages Processed Successfully: " + successCount.get());
        System.out.println("Total Errors Encountered: " + errorCount.get());
    }
}