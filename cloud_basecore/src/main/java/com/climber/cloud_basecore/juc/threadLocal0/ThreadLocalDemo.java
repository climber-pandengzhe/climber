package com.climber.cloud_basecore.juc.threadLocal0;

import lombok.Data;

/**
 * 需求:线程隔离
 * 多线程并发场景下,每个线程的变量都是相互独立
 * 线程A: 设置(变量1) 获取(变量1)
 * 线程B: 设置(变量2) 获取(变量2)
 *
 * ThreadLocal:
 *      1 .set() 将变量绑定到当前线程中
 *      2 .get() 获取当前线程绑定的变量
 *
 */


@Data
public class ThreadLocalDemo {

    private volatile String content;

    public static void main(String[] args) {
        ThreadLocalDemo threadLocalDemo = new ThreadLocalDemo();

        String s="asdf ";
        String s2=s;
        s.trim();
        System.out.println(s2);
        System.out.println(s);

//        for (int i = 0; i < 5; i++) {
//            new Thread(() -> {
//                /**
//                 * 每个线程存一个变量,过一会取出这个变量
//                 */
//                threadLocalDemo.setContent(Thread.currentThread().getName()+"的数据");
//                System.out.println("");
//                System.out.println(Thread.currentThread().getName()+"=====>"+threadLocalDemo.getContent());
//            }, "线程"+String.valueOf(i)).start();
//
//
//
//            /* 这段代码可能打印这个结果:
//
//            线程0=====>线程1的数据
//
//            线程2=====>线程2的数据
//            线程1=====>线程1的数据
//
//            线程3=====>线程3的数据
//
//            线程4=====>线程4的数据
//             */
//        }
    }

}
