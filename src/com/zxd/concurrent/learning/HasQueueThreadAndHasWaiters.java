package com.zxd.concurrent.learning;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author CoderZZ
 * @Title: ${FILE_NAME}
 * @Project: ConcurrentLearning
 * @Package com.zxd.concurrent.learning
 * @description: 方法hasQueueThread(Thread thread)的作用是查询指定的线程是否正在等待获取此锁定
 * @description: 方法hasQueueThreads()的作用是查询是否有线程正在等待获取此锁定
 * @description: 方法hasWaiters(Condition condition)的作用是查询是否有线程正在等待与此锁定有关的condition条件
 * @Version 1.0
 * @create 2018-04-07 23:12
 **/
public class HasQueueThreadAndHasWaiters {
    public ReentrantLock reentrantLock = new ReentrantLock();
    public Condition condition = reentrantLock.newCondition();
    public void waitMethod(){
        try {
            reentrantLock.lock();
            condition.await();
//            Thread.sleep(Integer.MAX_VALUE);
            System.out.println("线程"+Thread.currentThread().getName()+"被唤醒执行........");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            reentrantLock.unlock();
        }
    }
    public void notifyMethod(){
        reentrantLock.lock();
        System.out.println("有没有线程正在等待condition?"+reentrantLock.hasWaiters(condition)+" 线程数是多少?"+reentrantLock.getWaitQueueLength(condition));
        condition.signal();
        reentrantLock.unlock();
    }
    public static void main(String[] args) throws InterruptedException {
        final HasQueueThreadAndHasWaiters hasQueueThreadAndHasWaiters = new HasQueueThreadAndHasWaiters();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                hasQueueThreadAndHasWaiters.waitMethod();
            }
        };
//        Thread threadA = new Thread(runnable);
//        threadA.start();
//        Thread.sleep(500);
//        Thread threadB = new Thread(runnable);
//        threadB.start();
//        Thread.sleep(500);
//        System.out.println(hasQueueThreadAndHasWaiters.reentrantLock.hasQueuedThread(threadA));
//        System.out.println(hasQueueThreadAndHasWaiters.reentrantLock.hasQueuedThread(threadB));
//        System.out.println(hasQueueThreadAndHasWaiters.reentrantLock.hasQueuedThreads());

        for(int i=0;i<10;i++){
            Thread thread = new Thread(runnable);
            thread.start();
        }
        Thread.sleep(2000);
        for(int i=0;i<10;i++) {
            hasQueueThreadAndHasWaiters.notifyMethod();
        }
    }
}
