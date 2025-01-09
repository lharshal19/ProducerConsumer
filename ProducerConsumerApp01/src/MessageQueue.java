

import java.util.LinkedList;
import java.util.Queue;

// MessageQueue class to simulate a message queue
class MessageQueue {
    private final Queue<Message> queue = new LinkedList<>();

    public synchronized void produce(Message message) {
        queue.add(message);
        notify();
    }

    public synchronized Message consume() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        return queue.poll();
    }
}