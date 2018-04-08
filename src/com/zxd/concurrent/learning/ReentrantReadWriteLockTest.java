package com.zxd.concurrent.learning;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author CoderZZ
 * @Title: ${FILE_NAME}
 * @Project: ConcurrentLearning
 * @Package com.zxd.concurrent.learning
 * @description: 读锁不互斥;读写、写写互斥
 * @Version 1.0
 * @create 2018-04-08 21:29
 **/
public class ReentrantReadWriteLockTest {

    private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    public void read(){
        try {
            reentrantReadWriteLock.readLock().lock();
            System.out.println("获得读锁"+Thread.currentThread().getName()+" timestamp:"+System.currentTimeMillis());
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            reentrantReadWriteLock.readLock().unlock();
        }
    }

    public void write(){
        try{
            reentrantReadWriteLock.writeLock().lock();
            System.out.println("获得写锁"+Thread.currentThread().getName()+" timestamp:"+System.currentTimeMillis());
            Thread.sleep(10000);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            reentrantReadWriteLock.writeLock().unlock();
        }
    }

    public static void main(String[] args){
        ReentrantReadWriteLockTest reentrantReadWriteLockTest = new ReentrantReadWriteLockTest();
        Runnable readRunnable = new Runnable() {
            @Override
            public void run() {
                reentrantReadWriteLockTest.read();
            }
        };
        Thread threadA = new Thread(readRunnable);
        threadA.setName("A");
        threadA.start();
        Thread threadB = new Thread(readRunnable);
        threadB.setName("B");
        threadB.start();
        Runnable writeRunnable = new Runnable() {
            @Override
            public void run() {
                reentrantReadWriteLockTest.write();
            }
        };
        Thread writeA = new Thread(writeRunnable);
        writeA.setName("writeA");
        writeA.start();
        Thread writeB = new Thread(writeRunnable);
        writeB.setName("writeB");
        writeB.start();
    }
}
