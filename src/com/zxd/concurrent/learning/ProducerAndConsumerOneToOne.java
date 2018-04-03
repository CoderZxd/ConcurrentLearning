package com.zxd.concurrent.learning;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author CoderZZ
 * @Title: ${FILE_NAME}
 * @Project: ConcurrentLearning
 * @Package com.zxd.concurrent.learning
 * @description: 使用Condition实现生产者/消费者模式：一对一交替生产消费鸡蛋
 * @Version 1.0
 * @create 2018-04-03 23:29
 **/
public class ProducerAndConsumerOneToOne {

    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    private boolean hasEgg = false;

    public void putEgg(){
        try {
            lock.lock();
            while(hasEgg){
                condition.await();
            }
            System.out.println("Thread Name:"+Thread.currentThread().getName()+" put(+) egg!");
            hasEgg = true;
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void getEgg(){
        try {
            lock.lock();
            while(!hasEgg){
                condition.await();
            }
            System.out.println("Thread Name:"+Thread.currentThread().getName()+" get(-) egg!");
            hasEgg = false;
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    static class ProducerThread extends Thread{
        private ProducerAndConsumerOneToOne producerAndConsumerOneToOne;

        public ProducerThread(ProducerAndConsumerOneToOne producerAndConsumerOneToOne){
            this.producerAndConsumerOneToOne = producerAndConsumerOneToOne;
        }

        @Override
        public void run(){
            while (true){
                try {
                    Thread.sleep(500);
                    producerAndConsumerOneToOne.putEgg();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class ConsumerThread extends Thread{
        private ProducerAndConsumerOneToOne producerAndConsumerOneToOne;

        public ConsumerThread(ProducerAndConsumerOneToOne producerAndConsumerOneToOne){
            this.producerAndConsumerOneToOne = producerAndConsumerOneToOne;
        }

        @Override
        public void run(){
            while (true){
                try {
                    Thread.sleep(500);
                    producerAndConsumerOneToOne.getEgg();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ProducerAndConsumerOneToOne producerAndConsumerOneToOne = new ProducerAndConsumerOneToOne();
        ProducerThread producerThread = new ProducerThread(producerAndConsumerOneToOne);
        producerThread.setName("producerThread_1");
        producerThread.start();
        Thread.sleep(500);
        ConsumerThread consumerThread = new ConsumerThread(producerAndConsumerOneToOne);
        consumerThread.setName("consumerThread_1");
        consumerThread.start();
    }

}
