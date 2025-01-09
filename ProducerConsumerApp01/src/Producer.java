
// Producer class
class Producer implements Runnable {
    private final MessageQueue queue;

    public Producer(MessageQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            String content = "Message " + i;
            Message message = new Message(content);
            queue.produce(message);
            System.out.println("Produced: " + content);
            try {
                Thread.sleep(500); // Simulate production delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}