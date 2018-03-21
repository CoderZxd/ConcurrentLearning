package com.zxd.concurrent.learning;

import java.util.concurrent.Semaphore;

/**
 * @Project ConcurrentLearning
 * @Package com.zxd.concurrent.learning
 * @Author：zouxiaodong
 * @Description:
 * @Date:Created in 18:13 2018/3/21.
 */
public class SemaphoreTest {

    public static void main(String[] args){
        int person = 8;
        Semaphore semaphore = new Semaphore(5);
        for(int i = 0;i < person;i++){
            new Thread(new Person(person,semaphore)).start();
        }
    }
    static class Person implements Runnable{
        private int num;
        private Semaphore semaphore;

        public Person(int num, Semaphore semaphore) {
            this.num = num;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                System.out.println("1.线程："+Thread.currentThread().getName()+"准备获取许可,可用semaphore数:"+semaphore.availablePermits());
                semaphore.acquire();
                System.out.println("2.线程："+Thread.currentThread().getName()+"占用一个许可,可用semaphore数:"+semaphore.availablePermits());
                Thread.sleep(2000);
                semaphore.release();
                System.out.println("3.线程："+Thread.currentThread().getName()+"释放一个许可,可用semaphore数:"+semaphore.availablePermits());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
