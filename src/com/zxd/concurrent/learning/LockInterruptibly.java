package com.zxd.concurrent.learning;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author CoderZZ
 * @Title: ${FILE_NAME}
 * @Project: ConcurrentLearning
 * @Package com.zxd.concurrent.learning
 * @description: 方法void interruptibly的作用是:如果当前线程未被中断，则获取锁定，如果已经被中断则出现异常
 * @Version 1.0
 * @create 2018-04-07 23:39
 **/
public class LockInterruptibly {
    public ReentrantLock reentrantLock = new ReentrantLock();
    private Condition condition = reentrantLock.newCondition();
    public void waitMethod(){
        try {
//            reentrantLock.lock();
            reentrantLock.lockInterruptibly();
            System.out.println("Lock begin "+Thread.currentThread().getName());
            for(int i=0;i<Integer.MAX_VALUE/10;i++){
                String string = new String();
                Math.random();
            }
            System.out.println("Lock end "+Thread.currentThread().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(reentrantLock.isHeldByCurrentThread()){
                reentrantLock.unlock();
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        final LockInterruptibly lockInterruptibly = new LockInterruptibly();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                lockInterruptibly.waitMethod();
            }
        };
        Thread threadA = new Thread(runnable);
        threadA.setName("A");
        threadA.start();
        Thread.sleep(500);
        Thread threadB = new Thread(runnable);
        threadB.setName("B");
        threadB.start();
        threadB.interrupt();
        System.out.println("main end!");
    }
}
