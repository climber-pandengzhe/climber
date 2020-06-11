package com.climber.cloud_basecore.thread;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class SyncExample4 {


    static Object object = new Object();
    static Apple apple = new Apple();

    public static void main(String[] args) {
        // 查看HashCode
        System.out.println(Integer.toHexString(apple.hashCode()));
        System.out.println(ClassLayout.parseInstance(object).toPrintable());

        System.out.println(ClassLayout.parseInstance(new AbstractQueuedSynchronizer() {
            @Override
            protected boolean tryAcquire(int arg) {
                return super.tryAcquire(arg);
            }
        }).toPrintable());
    }
}

class Apple {
    private int count;
    private int count2;
    private int count3;
    private int count4;
    private boolean isMax;

    public Apple(int count, int count2, int count3, int count4, boolean isMax) {
        this.count = count;
        this.count2 = count2;
        this.count3 = count3;
        this.count4 = count4;
        this.isMax = isMax;
    }

    public Apple() {
    }


}
