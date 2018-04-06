package com.zxd.concurrent.learning;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author CoderZZ
 * @Title: ${FILE_NAME}
 * @Project: ConcurrentLearning
 * @Package com.zxd.concurrent.learning
 * @description: 公平锁与非公平锁
 * @Version 1.0
 * @create 2018-04-05 23:34
 **/
public class FairOrNoFairLock {

    private ReentrantLock lock;

    public FairOrNoFairLock(boolean isFair){
        this.lock = new ReentrantLock(isFair);
    }

    public void serviceMethod(){
        try {
            lock.lock();
            Thread.sleep(1000);
            System.out.println("Thread Name:"+Thread.currentThread().getName()+"获得锁，getHoldCount = "+lock.getHoldCount()+",getQueueLength = "+lock.getQueueLength());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void fair(){
        FairOrNoFairLock fairOrNoFairLock = new FairOrNoFairLock(true);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("★线程 "+Thread.currentThread().getName() +" 运行了 ");
                fairOrNoFairLock.serviceMethod();
            }
        };
        for(int i=0;i<10;i++){
            Thread thread = new Thread(runnable);
            thread.start();
        }
    }
    public static void noFair(){
        FairOrNoFairLock fairOrNoFairLock = new FairOrNoFairLock(false);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("★线程 "+Thread.currentThread().getName() +" 运行了 ");
                fairOrNoFairLock.serviceMethod();
            }
        };
        for(int i=0;i<10;i++){
            Thread thread = new Thread(runnable);
            thread.start();
        }
    }

    public static void main(String[] args){
//        fair();
        noFair();
    }
}
