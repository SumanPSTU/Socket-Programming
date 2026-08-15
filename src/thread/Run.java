package thread;

public class Run {
    public static void main(String[] args) {
        RunnableThread r1 = new RunnableThread("Thread A");
        RunnableThread r2 = new RunnableThread("Thread B");

        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);

        // 1. Start the threads first
        t1.start();
        t2.start();

        // 2. Now check if they are alive (will print true)
        System.out.println("Thread A alive " + t1.isAlive());
        System.out.println("Thread B alive " + t2.isAlive());

        try {
            // 3. Join the threads so main waits for them to finish
            t1.join();
            t2.join(); // Good practice to join both if you want to wait for both
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Main method finished.");
        System.out.println("Thread A alive " + t1.isAlive());
        System.out.println("Thread B alive " + t2.isAlive());

    }
}