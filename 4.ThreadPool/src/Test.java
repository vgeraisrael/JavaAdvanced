import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) {
        int rt = (int)(Runtime.getRuntime().availableProcessors() * 2.2);
        System.out.println("Number of Threads: " + rt);
        ExecutorService es = Executors.newFixedThreadPool(rt);

        for (int i = 0; i < 25; i++) {
            es.submit(new Work(i));
        }

        es.shutdown();
        System.out.println("All tasks submited");
        try {
            es.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class Work implements Runnable {

    private int id;

    public Work(int id){
        this.id = id;
    }

    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Work # " + id + " was comnpleted");
    }
}
