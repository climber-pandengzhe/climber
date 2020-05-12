package com.climber.cloud_basecore.juc;

import com.climber.cloud_basecore.EnumCountry;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 0; i <=5 ; i++) {
            new Thread(
                    () ->{
                        System.out.println(Thread.currentThread().getName()+"\t 国被灭");
                        countDownLatch.countDown();
                    }, EnumCountry.forEach_EnumCountry(i+1).getRetMessage()
            ).start();
        }

        countDownLatch.await();
        System.out.println("秦国统一全国");


    }


    public static void closeDoor(String[] args) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i <=10 ; i++) {
            new Thread(
                    () ->{
                        System.out.println(Thread.currentThread().getName()+"\t上完自习,离开教室");
                        countDownLatch.countDown();
                    }
            ).start();
        }

        countDownLatch.await();
        System.out.println(Thread.currentThread().getName()+"\t ****班长关门走人");



    }
}
