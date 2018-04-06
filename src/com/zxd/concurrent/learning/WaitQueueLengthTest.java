package com.zxd.concurrent.learning;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author CoderZZ
 * @Title: ${FILE_NAME}
 * @Project: ConcurrentLearning
 * @Package com.zxd.concurrent.learning
 * @description: TODO:一句话描述信息
 * @Version 1.0
 * @create 2018-04-06 23:40
 **/
public class WaitQueueLengthTest {
    private ReentrantLock reentrantLock = new ReentrantLock();
    private Condition condition = reentrantLock.newCondition();
    public void waitMethod(){
        try {
            reentrantLock.lock();
            System.out.println(Thread.currentThread().getName()+" waiting......"+"有"+reentrantLock.getWaitQueueLength(condition)+"个线程正在等待condition");
            condition.await();
            System.out.println(Thread.currentThread().getName()+" running......"+"有"+reentrantLock.getWaitQueueLength(condition)+"个线程正在等待condition");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            reentrantLock.unlock();
        }

    }
    public void notifyMethod(){
        reentrantLock.lock();
        System.out.println("有"+reentrantLock.getWaitQueueLength(condition)+"个线程正在等待condition");
//        condition.signalAll();
        condition.signal();
        reentrantLock.unlock();
    }

    public static void main(String[] args) throws InterruptedException {
        WaitQueueLengthTest waitQueueLengthTest = new WaitQueueLengthTest();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                waitQueueLengthTest.waitMethod();
            }
        };
        for(int i=0;i<10;i++){
            Thread thread = new Thread(runnable);
            thread.start();
        }
        Thread.sleep(1000);
        for(int i=0;i<10;i++){
            waitQueueLengthTest.notifyMethod();
            Thread.sleep(100);
        }
    }
}
