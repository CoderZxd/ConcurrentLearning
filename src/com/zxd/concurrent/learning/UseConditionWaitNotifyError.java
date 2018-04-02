package com.zxd.concurrent.learning;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author CoderZZ
 * @Title: ${FILE_NAME}
 * @Project: ConcurrentLearning
 * @Package com.zxd.concurrent.learning
 * @description: 使用Condition实现等待/通知：错误用法与解决
 * @Version 1.0
 * @create 2018-04-02 23:20
 **/
public class UseConditionWaitNotifyError {

    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    public void await(){
        try {
            System.out.println("调用lock.lock()");
//            lock.lock();//必须在condition.await()之前调用lock.lock()方法获得同步监视器，去掉会提示java.lang.IllegalMonitorStateException异常
            System.out.println("Before await()");
            condition.await();
//            condition.awaitNanos(5000000000L);//10^9纳秒=1秒
//            condition.await(5, TimeUnit.SECONDS);
            System.out.println("After await()");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
            System.out.println("lock.unlock()");
        }
    }

    static class ThreadA extends  Thread{

        private UseConditionWaitNotifyError useConditionWaitNotifyError;

        public ThreadA(UseConditionWaitNotifyError useConditionWaitNotifyError){
            this.useConditionWaitNotifyError = useConditionWaitNotifyError;
        }

        @Override
        public void  run(){
            useConditionWaitNotifyError.await();
        }
    }

    public static void  main(String[] args){
        UseConditionWaitNotifyError useConditionWaitNotifyError = new UseConditionWaitNotifyError();
        ThreadA threadA = new ThreadA(useConditionWaitNotifyError);
        threadA.start();
    }
}
