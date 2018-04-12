package threadlocal;

/**
 * @author CoderZZ
 * @Title: ${FILE_NAME}
 * @Project: ConcurrentLearning
 * @Package threadlocal
 * @description: TODO:一句话描述信息
 * @Version 1.0
 * @create 2018-04-12 22:39
 **/
public class ThreadlocalTools {

    public static MyThreadLocal<String> threadLocal = new MyThreadLocal<String>();

    static class MyThreadLocal<T> extends ThreadLocal<T>{
        @Override
        protected T initialValue() {
            return (T)Thread.currentThread().getName();
        }
    }
    static class RunnableThread implements Runnable{
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+"----threadLocal before:"+ThreadlocalTools.threadLocal.get());
            ThreadlocalTools.threadLocal.set(Thread.currentThread().getName());
            System.out.println(Thread.currentThread().getName()+"----threadLocal after:"+ThreadlocalTools.threadLocal.get());
        }
    }

    public static void main(String[] args){
        RunnableThread runnableThread = new RunnableThread();
        Thread threadA = new Thread(runnableThread,"Thread-A");
        threadA.start();
        Thread threadB = new Thread(runnableThread,"Thread-B");
        threadB.start();
    }
}
