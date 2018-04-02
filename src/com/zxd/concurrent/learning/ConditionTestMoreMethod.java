package com.zxd.concurrent.learning;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author CoderZZ
 * @Title: ${FILE_NAME}
 * @Project: ConcurrentLearning
 * @Package com.zxd.concurrent.learning
 * @description: TODO:一句话描述信息
 * @Version 1.0
 * @create 2018-04-02 22:43
 **/
public class ConditionTestMoreMethod {

    private Lock lock = new ReentrantLock();

    public void methodA(){
        try {
            lock.lock();
            System.out.println("methodA begin ThreadName="+Thread.currentThread().getName() +",time="+System.currentTimeMillis());
            Thread.sleep(1000);
            System.out.println("methodA begin ThreadName="+Thread.currentThread().getName() +",time="+System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void methodB(){
        try {
            lock.lock();
            System.out.println("methodB begin ThreadName="+Thread.currentThread().getName() +",time="+System.currentTimeMillis());
            Thread.sleep(1000);
            System.out.println("methodB begin ThreadName="+Thread.currentThread().getName() +",time="+System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    static class ThreadA extends Thread{

        private ConditionTestMoreMethod conditionTestMoreMethod;

        public ThreadA(ConditionTestMoreMethod conditionTestMoreMethod){
            this.conditionTestMoreMethod = conditionTestMoreMethod;
        }

        @Override
        public void run(){
            conditionTestMoreMethod.methodA();
        }
    }

    static class ThreadAA extends Thread{

        private ConditionTestMoreMethod conditionTestMoreMethod;

        public ThreadAA(ConditionTestMoreMethod conditionTestMoreMethod){
            this.conditionTestMoreMethod = conditionTestMoreMethod;
        }

        @Override
        public void run(){
            conditionTestMoreMethod.methodA();
        }
    }

    static class ThreadB extends Thread{

        private ConditionTestMoreMethod conditionTestMoreMethod;

        public ThreadB(ConditionTestMoreMethod conditionTestMoreMethod){
            this.conditionTestMoreMethod = conditionTestMoreMethod;
        }

        @Override
        public void run(){
            conditionTestMoreMethod.methodB();
        }
    }

    static class ThreadBB extends Thread{

        private ConditionTestMoreMethod conditionTestMoreMethod;

        public ThreadBB(ConditionTestMoreMethod conditionTestMoreMethod){
            this.conditionTestMoreMethod = conditionTestMoreMethod;
        }

        @Override
        public void run(){
            conditionTestMoreMethod.methodB();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ConditionTestMoreMethod conditionTestMoreMethod = new ConditionTestMoreMethod();
        ThreadA threadA = new ThreadA(conditionTestMoreMethod);
        threadA.setName("A");
        threadA.start();

        ThreadAA threadAA = new ThreadAA(conditionTestMoreMethod);
        threadAA.setName("AA");
        threadAA.start();

        Thread.sleep(100);

        ThreadB threadB = new ThreadB(conditionTestMoreMethod);
        threadB.setName("B");
        threadB.start();

        ThreadBB threadBB = new ThreadBB(conditionTestMoreMethod);
        threadBB.setName("BB");
        threadBB.start();
    }
}
