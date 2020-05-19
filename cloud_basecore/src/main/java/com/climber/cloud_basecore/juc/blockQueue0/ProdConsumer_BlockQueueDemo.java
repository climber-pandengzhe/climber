package com.climber.cloud_basecore.juc.blockQueue0;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShareData{
    private int num=0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    public void increment() throws  Exception{
        lock.lock();
        while (num!=0){
            condition.await();
        }

        num=num+1;
        condition.signalAll();
        System.out.println(Thread.currentThread().getName()+"num:"+num);
        lock.unlock();

    }

    public void decrement() throws  Exception{
        lock.lock();
        while (num==0){
            condition.await();
        }

        num=num-1;
        condition.signalAll();
        System.out.println(Thread.currentThread().getName()+"num:"+num);
        lock.unlock();


    }
}


/**
 * 一个初始值为0的变量,两个线程对其交替操作,一个加一,一个减一,操作五次
 */
public class ProdConsumer_BlockQueueDemo {
    public static void main(String[] args) {
        ShareData shareData= new ShareData();
        new Thread(() -> {
            for (int i = 0; i <9 ; i++) {
                try {
                    shareData.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
         }, "AA").start();

        new Thread(() -> {
            for (int i = 0; i <9 ; i++) {
                try {
                    shareData.decrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "BB").start();

        new Thread(() -> {
            for (int i = 0; i <9 ; i++) {
                try {
                    shareData.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "CC").start();

        new Thread(() -> {
            for (int i = 0; i <9 ; i++) {
                try {
                    shareData.decrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "DD").start();

    }
}
