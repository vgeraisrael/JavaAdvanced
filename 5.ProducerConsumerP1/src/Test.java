import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Test {
    private static BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                produce();
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                consumer();
            }
        });

        thread1.start();
        thread2.start();

//        thread1.join();
//        thread2.join();
    }
    private static void produce() {
        Random random = new Random();

        while (true) {
            try {
                queue.put(random.nextInt(100));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void consumer() {
        Random rnd = new Random();
        while (true) {
            try {
                Thread.sleep(100);
                if (rnd.nextInt(10) == 5) {
                    queue.take();
                    System.out.println("Queue size is " + queue.size());
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
