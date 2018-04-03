package com.zxd.concurrent.learning;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author CoderZZ
 * @Title: ${FILE_NAME}
 * @Project: ConcurrentLearning
 * @Package com.zxd.concurrent.learning
 * @description: 使用condition实现唤醒所有阻塞线程
 * @Version 1.0
 * @create 2018-04-03 22:44
 **/
public class ConditionWakeUpAllThreadTest {

    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    public void awaitA(){
        try {
            lock.lock();
            System.out.println("begin awaitA时间为："+System.currentTimeMillis()+" Thread Name:"+ Thread.currentThread().getName());
            condition.await();
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
            condition.await();
            System.out.println("end awaitB时间为："+System.currentTimeMillis()+" Thread Name:"+ Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }

    public void signalAll(){
        lock.lock();
        System.out.println("begin signalAll时间为："+System.currentTimeMillis()+" Thread Name:"+ Thread.currentThread().getName());
        condition.signalAll();
//        condition.signal();  //只会唤醒线程A，线程B仍然阻塞
        lock.unlock();
    }

    static class ThreadA extends Thread{
        private ConditionWakeUpAllThreadTest conditionWakeUpAllThreadTest;

        public ThreadA(ConditionWakeUpAllThreadTest conditionWakeUpAllThreadTest){
            this.conditionWakeUpAllThreadTest = conditionWakeUpAllThreadTest;
        }

        @Override
        public  void run(){
            conditionWakeUpAllThreadTest.awaitA();
        }
    }

    static class ThreadB extends Thread{
        private ConditionWakeUpAllThreadTest conditionWakeUpAllThreadTest;

        public ThreadB(ConditionWakeUpAllThreadTest conditionWakeUpAllThreadTest){
            this.conditionWakeUpAllThreadTest = conditionWakeUpAllThreadTest;
        }

        @Override
        public  void run(){
            conditionWakeUpAllThreadTest.awaitB();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ConditionWakeUpAllThreadTest conditionWakeUpAllThreadTest = new ConditionWakeUpAllThreadTest();
        ThreadA threadA = new ThreadA(conditionWakeUpAllThreadTest);
        threadA.setName("A");
        threadA.start();
        ThreadB threadB = new ThreadB(conditionWakeUpAllThreadTest);
        threadB.setName("B");
        threadB.start();
        Thread.sleep(3000);
        conditionWakeUpAllThreadTest.signalAll();
    }
}
