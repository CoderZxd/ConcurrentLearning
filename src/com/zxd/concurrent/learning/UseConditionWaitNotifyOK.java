package com.zxd.concurrent.learning;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author CoderZZ
 * @Title: ${FILE_NAME}
 * @Project: ConcurrentLearning
 * @Package com.zxd.concurrent.learning
 * @description: 正确使用Condition实现等待/通知
 * @Version 1.0
 * @create 2018-04-02 23:36
 **/
public class UseConditionWaitNotifyOK {

    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    public void await(){
        try {
            lock.lock();
            System.out.println("Thread Name :"+Thread.currentThread().getName()+" await 时间为："+System.currentTimeMillis());
            condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void signal(){
        try{
            lock.lock();
            System.out.println("Thread Name :"+Thread.currentThread().getName()+" signal时间为："+System.currentTimeMillis());
            condition.signal();
        }finally {
            lock.unlock();
        }
    }

    static class ThreadA extends Thread{

        private UseConditionWaitNotifyOK useConditionWaitNotifyOK;

        public ThreadA(UseConditionWaitNotifyOK useConditionWaitNotifyOK){
            this.useConditionWaitNotifyOK = useConditionWaitNotifyOK;
        }

        @Override
        public void run(){
            useConditionWaitNotifyOK.await();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        UseConditionWaitNotifyOK useConditionWaitNotifyOK = new UseConditionWaitNotifyOK();
        ThreadA threadA = new ThreadA(useConditionWaitNotifyOK);
        threadA.start();
        Thread.sleep(3000);
        useConditionWaitNotifyOK.signal();
    }
}
