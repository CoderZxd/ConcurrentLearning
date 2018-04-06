package com.zxd.concurrent.learning;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author CoderZZ
 * @Title: ${FILE_NAME}
 * @Project: ConcurrentLearning
 * @Package com.zxd.concurrent.learning
 * @description: TODO:一句话描述信息
 * @Version 1.0
 * @create 2018-04-06 23:00
 **/
public class LockHoldCountMethod {
    private ReentrantLock reentrantLock = new ReentrantLock();
    public void waitQueue(){
        try {
            reentrantLock.lock();
            Thread.sleep(200);
//            serviceMethod1();
            System.out.println("Thread name:"+Thread.currentThread().getName()+"started,getQueueLength:"+reentrantLock.getQueueLength());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            reentrantLock.unlock();
        }
    }
    public void serviceMethod1(){
        reentrantLock.lock();
        System.out.println("serviceMethod1 getHoldCount = "+reentrantLock.getHoldCount());
        serviceMethod2();
        reentrantLock.unlock();
    }
    public void serviceMethod2(){
        reentrantLock.lock();
        System.out.println("serviceMethod2 getHoldCount = "+reentrantLock.getHoldCount());
        reentrantLock.unlock();
    }
    public static void main(String[] args){
        LockHoldCountMethod lockHoldCountMethod = new LockHoldCountMethod();
//        lockHoldCountMethod.serviceMethod1();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                lockHoldCountMethod.waitQueue();
            }
        };
        for(int i=0;i<10;i++){
            Thread thread = new Thread(runnable);
            thread.start();
        }
    }

}
