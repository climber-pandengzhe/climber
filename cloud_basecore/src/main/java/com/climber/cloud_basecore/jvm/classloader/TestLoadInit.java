package com.climber.cloud_basecore.jvm.classloader;


/**
 * 对于静态字段来说,只有直接定义了该字段的类才会被初始化.
 * 当一个类在初始化时,要求其父类全部初始化完毕
 */
public class TestLoadInit {
    public static void main(String[] args) {
       //str 是final修饰的,不会触发初始化
        System.out.println(MySon.str);
    }

}

class MyParent{
    public final static String str= "hello parent";
    static {
        System.out.println("MyParent static block");
    }
}

class MySon extends MyParent{
    public static String str2="hello son";
    static {
        System.out.println("MyChild static block");
    }
}
