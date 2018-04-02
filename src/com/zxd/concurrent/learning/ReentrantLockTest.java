package com.zxd.concurrent.learning;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author CoderZZ
 * @Title: ${FILE_NAME}
 * @Project: ConcurrentLearning
 * @Package com.zxd.concurrent.learning
 * @description: TODO:一句话描述信息
 * @Version 1.0
 * @create 2018-04-02 21:03
 **/
public class ReentrantLockTest {

    private Lock lock = new ReentrantLock();

    public void testMethod(){
        lock.lock();
        for(int i=0;i<5;i++){
            System.out.println("Thread Name :"+Thread.currentThread().getName()+ " " +(i+1));
        }
        lock.unlock();
    }

    static class MyThread extends Thread{

        private ReentrantLockTest reentrantLockTest;

        public MyThread(ReentrantLockTest reentrantLockTest){
            this.reentrantLockTest = reentrantLockTest;
        }

        @Override
        public void run() {
            reentrantLockTest.testMethod();
        }

    }

    public static void main(String[] args){

        ReentrantLockTest reentrantLockTest = new ReentrantLockTest();
        List<MyThread> threadList = new ArrayList<MyThread>(10);
        for(int i=0;i<5;i++){
            threadList.add(new MyThread(reentrantLockTest));
        }
        for(MyThread myThread:threadList){
            myThread.start();
        }
    }
}
