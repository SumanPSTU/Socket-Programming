package thread;
public class RunnableThread implements Runnable{
    private String name;
    public RunnableThread(String name){
        this.name = name;
    }
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(this.name+" is working on step "+i);
            try {
                Thread.sleep(100);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println("Both thread are Done");
    }
}