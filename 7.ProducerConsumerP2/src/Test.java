import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

public class Test {


    public static void main(String[] args) {
        ProduserAndConsumer pac = new ProduserAndConsumer();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    pac.produce();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    pac.consume();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        t1.start();
        t2.start();
    }
}

class ProduserAndConsumer {
    private Queue<Integer> queue = new LinkedList();
    private final int LIMIT = 10;
    private final Object lock = new Object();

    public void produce() throws InterruptedException {
        int value = 0;
        while (true) {
            synchronized (lock) {
                while (queue.size() == LIMIT) {
                    lock.wait();
                }
                queue.offer(value++);
                lock.notify();
                System.out.println("added " + value);
            }
        }
    }

    public void consume() throws InterruptedException {
        while (true) {
            synchronized (lock) {
                while (queue.size() == 0) {
                    lock.wait();
                }
                int val = queue.poll();
                lock.notify();
                System.out.println(val);
            }
        }
    }
}
