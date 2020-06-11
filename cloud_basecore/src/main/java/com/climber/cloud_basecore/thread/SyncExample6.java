package com.climber.cloud_basecore.thread;

import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;

public class SyncExample6 {

    static volatile int  index = 0;

    static SyncExample6 lock = new SyncExample6();



    public static void main(String[] args) throws InterruptedException {

        System.out.println("========");
        // 这里使用ClassLayout来查看
        System.out.println(ClassLayout.parseInstance(new ArrayList<>()).toPrintable());
        System.out.println("========");
        Object obj =new Object();

        System.out.println(ClassLayout.parseInstance(obj).toPrintable());
        System.out.println(ClassLayout.parseInstance(new Integer(1)).toPrintable());
        System.out.println("========");

        // index 累加 1000次，使用lambda表达式
        Runnable task = () -> {
            // 不加sync则不能保证原子操作
            // synchronized (lock) {
                for (int i = 0; i < 10000; i++) {
                    index++;
                }
            // }
        };

        // 启动五个线程来执行任务
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(task);
            thread.start();
        }

        // 为了代码直观直接睡眠等待结果，实际需要调用线程的join方法等待线程结束
        Thread.sleep(2000);
        System.out.println("index = " + index);


    }
}
