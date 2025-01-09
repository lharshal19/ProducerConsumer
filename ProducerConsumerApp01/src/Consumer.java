

import java.util.concurrent.atomic.AtomicInteger;

// Consumer class
class Consumer implements Runnable {
    private final MessageQueue queue;
    private final AtomicInteger successCount;
    private final AtomicInteger errorCount;

    public Consumer(MessageQueue queue, AtomicInteger successCount, AtomicInteger errorCount) {
        this.queue = queue;
        this.successCount = successCount;
        this.errorCount = errorCount;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message message = queue.consume();
                System.out.println("Consumed: " + message.getContent());
                successCount.incrementAndGet();
                Thread.sleep(300); // Simulate processing time
            } catch (InterruptedException e) {
                errorCount.incrementAndGet();
                System.err.println("Error: " + e.getMessage());
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}