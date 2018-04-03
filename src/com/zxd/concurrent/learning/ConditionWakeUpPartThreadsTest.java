package com.zxd.concurrent.learning;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author CoderZZ
 * @Title: ${FILE_NAME}
 * @Project: ConcurrentLearning
 * @Package com.zxd.concurrent.learning
 * @description: 使用Condition实现通知部分线程
 * @Version 1.0
 * @create 2018-04-03 23:02
 **/
public class ConditionWakeUpPartThreadsTest {
    private Lock lock = new ReentrantLock();
    private Condition conditionA = lock.newCondition();
    private Condition conditionB = lock.newCondition();

    public void awaitA(){
        try {
            lock.lock();
            System.out.println("begin awaitA时间为："+System.currentTimeMillis()+" Thread Name:"+ Thread.currentThread().getName());
            conditionA.await();
            System.out.println("end awaitA时间为："+System.currentTimeMillis()+" Thread Name:"+ Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void awaitB(){
        try {
            lock.lock();
            System.out.println("begin awaitB时间为："+System.currentTimeMillis()+" Thread Name:"+ Thread.currentThread().getName());
            conditionB.await();
            System.out.println("end awaitB时间为："+System.currentTimeMillis()+" Thread Name:"+ Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void signalAll_A(){
        lock.lock();
        System.out.println("begin signalAll_A时间为："+System.currentTimeMillis()+" Thread Name:"+ Thread.currentThread().getName());
        conditionA.signalAll();
        lock.unlock();
    }

    public void signalAll_B(){
        lock.lock();
        System.out.println("begin signalAll_B时间为："+System.currentTimeMillis()+" Thread Name:"+ Thread.currentThread().getName());
        conditionB.signalAll();
        lock.unlock();
    }

    static class ThreadA extends Thread{
        private ConditionWakeUpPartThreadsTest conditionWakeUpPartThreadsTest;

        public ThreadA(ConditionWakeUpPartThreadsTest conditionWakeUpPartThreadsTest){
            this.conditionWakeUpPartThreadsTest = conditionWakeUpPartThreadsTest;
        }

        @Override
        public  void run(){
            conditionWakeUpPartThreadsTest.awaitA();
        }
    }

    static class ThreadB extends Thread{
        private ConditionWakeUpPartThreadsTest conditionWakeUpPartThreadsTest;

        public ThreadB(ConditionWakeUpPartThreadsTest conditionWakeUpPartThreadsTest){
            this.conditionWakeUpPartThreadsTest = conditionWakeUpPartThreadsTest;
        }

        @Override
        public  void run(){
            conditionWakeUpPartThreadsTest.awaitB();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ConditionWakeUpPartThreadsTest conditionWakeUpPartThreadsTest = new ConditionWakeUpPartThreadsTest();
        ThreadA threadA_0 = new ThreadA(conditionWakeUpPartThreadsTest);
        threadA_0.setName("ThreadA_0");
        threadA_0.start();
        ThreadA threadA_1 = new ThreadA(conditionWakeUpPartThreadsTest);
        threadA_1.setName("ThreadA_1");
        threadA_1.start();
        Thread.sleep(1000);
        ThreadB threadB_0 = new ThreadB(conditionWakeUpPartThreadsTest);
        threadB_0.setName("ThreadB_0");
        threadB_0.start();
        ThreadB threadB_1 = new ThreadB(conditionWakeUpPartThreadsTest);
        threadB_1.setName("ThreadB_1");
        threadB_1.start();
        Thread.sleep(3000);
        conditionWakeUpPartThreadsTest.signalAll_A();
        Thread.sleep(3000);
        conditionWakeUpPartThreadsTest.signalAll_B();
    }
}
