package com.climber.cloud_basecore.jvm.classloader;


import java.util.UUID;

/**
 * 对于静态字段来说,只有直接定义了该字段的类才会被初始化.
 * 当一个类在初始化时,要求其父类全部初始化完毕
 * -XX:TraceClassLoading 用于追踪类的加载信息并打印出来.
 *
 * -XX:+<Option>表示开启option选项
 * -XX:-<Option>表示关闭option选项
 * -XX:<Option>=<value>表示将Option选项设置为value.
 */
public class TestLoadInit2 {
    public static void main(String[] args) {
        //当一个常量的值并非编译器可以确定的,那么其值就不会被放到调用类的常量池中.
        //这是程序运行时,会导致主动使用这个常量所在的类,显然会导致这个类被初始化

        System.out.println(MySon.str);
    }

}

class MyParent2{
    public  final static String str= UUID.randomUUID().toString();
    static {
        System.out.println("MyParent static block");
    }
}

