package com.zxd.concurrent.learning;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author CoderZZ
 * @Title: ${FILE_NAME}
 * @Project: ConcurrentLearning
 * @Package com.zxd.concurrent.learning
 * @description: 实现生产者/消费者模式：多对多交替生产消费鸡蛋
 * @Version 1.0
 * @create 2018-04-03 23:59
 **/
public class ProducerAndConsumerManyToMany {


    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    private boolean hasEgg = false;

    public void putEgg(){
        try {
            lock.lock();
            while(hasEgg){
                System.out.println("有可能连续生产鸡蛋★★!");
                condition.await();
            }
            System.out.println("Thread Name:"+Thread.currentThread().getName()+" 生产鸡蛋★!");
            hasEgg = true;
//            condition.signal();
            condition.signalAll();
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
                System.out.println("有可能连续消费鸡蛋☆☆!");
                condition.await();
            }
            System.out.println("Thread Name:"+Thread.currentThread().getName()+" 消费鸡蛋☆!");
            hasEgg = false;
//            condition.signal();
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    static class ProducerThread extends Thread{
        private ProducerAndConsumerManyToMany producerAndConsumerManyToMany;

        public ProducerThread(ProducerAndConsumerManyToMany producerAndConsumerManyToMany){
            this.producerAndConsumerManyToMany = producerAndConsumerManyToMany;
        }

        @Override
        public void run(){
            while (true){
                try {
                    BigDecimal bigDecimal = new BigDecimal(Math.random()*5000);
                    bigDecimal.setScale(0,BigDecimal.ROUND_HALF_UP);
                    Thread.sleep(bigDecimal.longValue());
                    producerAndConsumerManyToMany.putEgg();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class ConsumerThread extends Thread{
        private ProducerAndConsumerManyToMany producerAndConsumerManyToMany;

        public ConsumerThread(ProducerAndConsumerManyToMany producerAndConsumerManyToMany){
            this.producerAndConsumerManyToMany = producerAndConsumerManyToMany;
        }

        @Override
        public void run(){
            while (true){
                try {
                    BigDecimal bigDecimal = new BigDecimal(Math.random()*5000);
                    bigDecimal.setScale(0,BigDecimal.ROUND_HALF_UP);
                    Thread.sleep(bigDecimal.longValue());
                    producerAndConsumerManyToMany.getEgg();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ProducerAndConsumerManyToMany producerAndConsumerManyToMany = new ProducerAndConsumerManyToMany();
        List<ProducerThread> producersList = new ArrayList<ProducerThread>(10);
        List<ConsumerThread> consumersList = new ArrayList<ConsumerThread>(10);
        for(int i =0;i<10;i++){
            ProducerThread producer = new ProducerThread(producerAndConsumerManyToMany);
            producer.setName("producer_"+i);
            producer.start();
            ConsumerThread consumer = new ConsumerThread(producerAndConsumerManyToMany);
            consumer.setName("consumer_"+i);
            consumer.start();
        }
    }

}
