package com.climber.cloud_basecore.testpkg;

import org.openjdk.jol.info.ClassLayout;

/**
 * @Author zhoushengqiang
 * @Date 22/6/2020 10:25 AM
 * @Version 1.0
 */
public class TestLock {

    public static void main(String[] args) throws InterruptedException {
        Object o = new Object();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());

        Thread.sleep(5000);

        synchronized (o){

            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
    }
}
