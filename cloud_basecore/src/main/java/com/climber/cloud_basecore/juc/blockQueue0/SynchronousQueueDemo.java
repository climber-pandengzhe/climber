package com.climber.cloud_basecore.juc.blockQueue0;


import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * 线程AA,执行第一个put 1,然后阻塞,等线程bbtake之后,才会继续执行put 2
 */
public class SynchronousQueueDemo {
    public static void main(String[] args) {
        SynchronousQueue<String> synchronousQueue = new SynchronousQueue<>();
        new Thread(() -> {
            try {
            System.out.println(Thread.currentThread().getName()+"\t put 1");
            synchronousQueue.put("1");

            System.out.println(Thread.currentThread().getName()+"\t put 2");
            synchronousQueue.put("2");


            System.out.println(Thread.currentThread().getName()+"\t put 2");
            synchronousQueue.put("2");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "AA").start();


        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName()+"\t"+ synchronousQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName()+"\t"+ synchronousQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName()+"\t"+ synchronousQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }, "BB").start();


    }
}
