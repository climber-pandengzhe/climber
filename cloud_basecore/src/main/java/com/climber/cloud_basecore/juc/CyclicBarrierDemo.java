package com.climber.cloud_basecore.juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7,()->{
            System.out.println("召唤神龙");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("许愿完成了");
        });

        for (int i = 1; i <=7; i++) {
            final int template = i;
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName()+"收集到第"+template+"颗龙珠");

                try {
                    cyclicBarrier.await();
                    System.out.println("---神龙召唤完了,龙珠"+template+"飞往各处");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }


    }
}
