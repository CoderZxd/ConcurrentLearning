package synchronize;

/**
 * @author CoderZZ
 * @Title: ${FILE_NAME}
 * @Project: ConcurrentLearning
 * @Package PACKAGE_NAME
 * @description: TODO:一句话描述信息
 * @Version 1.0
 * @create 2018-04-19 1:26
 **/
public class DeadThread implements Runnable{

    private String username;
    private Object lock_1 = new Object();
    private Object lock_2 = new Object();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void run() {
        if(username.equals("a")){
            synchronized (lock_1){
                try {
                    System.out.println("username = "+username);
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock_2){
                    System.out.println("按lock_1->lock_2代码顺序执行了");
                }
            }
        }
        if(username.equals("b")){
            synchronized (lock_2){
                try {
                    System.out.println("username = "+username);
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock_1){
                    System.out.println("按lock_2->lock_1代码顺序执行了");
                }
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        DeadThread deadThread = new DeadThread();
        deadThread.setUsername("a");
        Thread t1 = new Thread(deadThread);
        t1.start();
        Thread.sleep(100);
        deadThread.setUsername("b");
        Thread t2 = new Thread(deadThread);
        t2.start();
    }
}
