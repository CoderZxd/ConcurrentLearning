package wait.notify;

/**
 * @author CoderZZ
 * @Title: ${FILE_NAME}
 * @Project: ConcurrentLearning
 * @Package wait.notify
 * @description: TODO:一句话描述信息
 * @Version 1.0
 * @create 2018-04-19 23:30
 **/
public class ObjectWaitAndNotify {

    static class Thread_1 extends Thread{

        private Object lock;

        public Thread_1(Object lock){
            this.lock = lock;
        }

        @Override
        public void run(){
            try {
                synchronized (lock){
                    System.out.println(Thread.currentThread().getName()+"    Before wait:"+System.currentTimeMillis());
                    lock.wait();
                    System.out.println(Thread.currentThread().getName()+"    After wait:"+System.currentTimeMillis());
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    static class Thread_2 extends Thread{

        private Object lock;

        public Thread_2(Object lock){
            this.lock = lock;
        }

        @Override
        public void run(){
            try {
                synchronized (lock){
                    System.out.println(Thread.currentThread().getName()+"    Before notify:"+System.currentTimeMillis());
                    lock.notify();
                    System.out.println(Thread.currentThread().getName()+"    After notify:"+System.currentTimeMillis());
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();
        Thread_1 thread_1 = new Thread_1(lock);
        thread_1.setName("thread-1");
        thread_1.start();
        Thread.sleep(3000);
        Thread_2 thread_2 = new Thread_2(lock);
        thread_2.setName("thread-2");
        thread_2.start();
    }
}
