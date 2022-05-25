import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        WaitAndNotify wan = new WaitAndNotify();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    wan.produce();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    wan.consume();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        t1.start();
        t2.start();
    }
}

class WaitAndNotify {
    public void produce() throws InterruptedException {
        synchronized (this) {
            System.out.println("Producer is started");
            wait(); //! When calling wait we're releasing intrinsic lock and waiting for notify
            System.out.println("Producer thread resumed");
        }
    }

    public void consume() throws InterruptedException {
        Thread.sleep(2000);
        Scanner scan = new Scanner(System.in);

        synchronized (this) {
            System.out.println("Waiting for return key is pressed");
            scan.nextLine();
            notify();
            Thread.sleep(5000);
        }
    }
}
