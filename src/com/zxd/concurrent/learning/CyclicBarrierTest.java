package com.zxd.concurrent.learning;

import java.util.concurrent.CyclicBarrier;

/**
 * @Project ConcurrentLearning
 * @Package com.zxd.concurrent.learning
 * @Author：zouxiaodong
 * @Description:
 * @Date:Created in 17:41 2018/3/21.
 */
public class CyclicBarrierTest {

    public static void main(String[] args) throws InterruptedException {
        int i = 4;
//        CyclicBarrier cyclicBarrier = new CyclicBarrier(i);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(i, new Runnable() {
            @Override
            public void run() {
                System.out.println("当前线程号为："+Thread.currentThread().getName()+",所有线程已到达barrier.................");
            }
        });

        for(int m=0;m<i;m++){
            new Writer(cyclicBarrier).start();
        }

        Thread.sleep(10000);

        System.out.println("CyclicBarrier重用....................");

        for(int m=0;m<i;m++){
            new Writer(cyclicBarrier).start();
        }

    }
    static class Writer extends Thread{
        private CyclicBarrier cyclicBarrier;

        public Writer(CyclicBarrier cyclicBarrier){
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run(){
            try {
                System.out.println("子线程"+Thread.currentThread().getName()+"正在执行写入.......");
                Thread.sleep(2000);
                System.out.println("子线程"+Thread.currentThread().getName()+"写入完成.......");
                cyclicBarrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("所有线程写入完成，继续处理线程"+Thread.currentThread().getName()+"其他任务..............");
        }
    }
}
