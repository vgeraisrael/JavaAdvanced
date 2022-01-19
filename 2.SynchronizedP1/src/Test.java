public class Test {

    private int counter = 0;

    public static void main(String[] args) throws InterruptedException {
        Test test = new Test();
        test.doWork();
    }

    public void doWork() throws InterruptedException {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 10_000; i++) {
//                    counter++;
                    increment();
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 10_000; i++) {
                   // counter++;
                    //! counter++ is not atomic and can`t be synchronized so we will use method increment
                    increment();
                }
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println(counter);
    }

    private synchronized void increment() { //!keyword synchronized don`t give permission to Thread to execute
        //!synchronized method, if another Thread already executing it
        counter++;
    }
}
