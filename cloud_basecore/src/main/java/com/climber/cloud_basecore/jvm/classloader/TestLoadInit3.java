package com.climber.cloud_basecore.jvm.classloader;


import java.util.UUID;

/**
 * 对于数组实例,其类型是由jvm在运行期间动态生成的,表示为[Lcom.climber.cloud_basecore.jvm.classloader.MyParent3 这种形式.
 * 动态生成的类型,其父类型就是Object
 * 对于数组来说,javaDoc经常将构成数组的元素为Component,实际就是将数组降低一个维度后的类型.
 * 助记符:
 * anewarray:表示创建一个引用类型的(比如类,接口,数组)数组,并将其引用至压入栈顶.
 * newarray:表示创建一个制定的原始类型(比如int,float,char等)数组,并将其引用值压入栈顶.
 */
public class TestLoadInit3 {
    public static void main(String[] args) {

        MyParent3[]  myParent3 = new MyParent3[1];
        System.out.println(myParent3.getClass());

        MyParent3[][]  myParent31 = new MyParent3[1][1];
        System.out.println(myParent31.getClass());
        System.out.println(myParent31.getClass().getSuperclass());

        System.out.println("------");

        int [] ints = new int[1];
        System.out.println(ints.getClass());
        System.out.println(ints.getClass().getSuperclass());
    }

}

class MyParent3{
    public  final static String str= UUID.randomUUID().toString();
    static {
        System.out.println("MyParent static block");
    }
}

