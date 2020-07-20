package com.climber.cloud_basecore.memory;

import java.util.ArrayList;
import java.util.List;

/**
 * 虚拟机栈:Stack Frame 栈帧
 * 程序计数器(Program Counter)
 * 本地方法栈:主要用于处理本地方法
 * 堆(Heap): JVM管理的最大的一块内存空间.与堆相关的一个重要概念是垃圾回收器,现代几乎所有的垃圾收集器都是采用的分代收集算法.
 * 所以堆空间也基于这一点进行了相应的划分.:新生代与老年代 Eden空间,From Survivor空间,To Survivor空间.
 * 方法区(Method Area):存储元信息.永久代(Permanent Generation).从JDK1.8开始,已经彻底废弃了永久代.使用元空间(mate space)
 * 运行常量池:方法区的一部分内容.
 * 直接内存: Direct Memory
 *
 */
public class MyTest1 {
    public static void main(String[] args) {
        List<MyTest1> list = new ArrayList<>();
        for(;;){
         list.add(new MyTest1());
     //    System.gc();
        }
    }

}
