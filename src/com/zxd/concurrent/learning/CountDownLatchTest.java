package com.zxd.concurrent.learning;

import java.util.concurrent.CountDownLatch;

/**
 * @Project ConcurrentLearning
 * @Package com.zxd.concurrent.learning
 * @Author：zouxiaodong
 * @Description:
 * @Date:Created in 10:57 2018/3/21.
 */
public class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);
        ThreadTest test_1 = new ThreadTest(latch,3000);
        ThreadTest test_2 = new ThreadTest(latch,5000);
        test_1.run();
        test_2.run();
        System.out.println("等待2个子线程执行完毕........");
        latch.await();
        System.out.println("2个子线程已经执行完毕..................");
        System.out.println("继续执行主线程..................");
        System.out.println("主线程其他逻辑执行..................");
    }
}

class ThreadTest implements Runnable{
    private CountDownLatch countDownLatch = null;
    private long sleep = 0L;

    public ThreadTest(CountDownLatch countDownLatch,long sleep) {
        this.countDownLatch = countDownLatch;
        this.sleep = sleep;
    }

    @Override
    public void run() {
        try {
            System.out.println("子线程"+Thread.currentThread().getName()+"正在执行.......");
            Thread.sleep(sleep);
            System.out.println("子线程"+Thread.currentThread().getName()+"执行完毕.......");
            System.out.println("--------------countDownLatch.getCount()前="+countDownLatch.getCount());
            countDownLatch.countDown();
            System.out.println("--------------countDownLatch.getCount()后="+countDownLatch.getCount());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}