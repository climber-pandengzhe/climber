package com.climber.cloud_basecore.jvm.classloader;


/**
 * 对于静态字段来说,只有直接定义了该字段的类才会被初始化.
 * 当一个类在初始化时,要求其父类全部初始化完毕
 * -XX:TraceClassLoading 用于追踪类的加载信息并打印出来.
 *
 * -XX:+<Option>表示开启option选项
 * -XX:-<Option>表示关闭option选项
 * -XX:<Option>=<value>表示将Option选项设置为value.
 */
public class TestLoadInit {
    public static void main(String[] args) {
        //str 是final修饰的,编译阶段,常量就会被存放在调用这个常亮的方法所在的类的常量池中.
        // 本质上,调用类并没有直接定义常亮的类,因此不会触发初始化 会加载,不会初始化.
        // 此处,将常量放在了 TestLoadInit 的常量池中,之后 TestLoadInit与 MyParent 就没有任何关系了.

        //助记符:
        //ldc将int,float或Stringl类型的常量值从常量池推送至栈顶
        //bipush 将单字节(-128 ~ 127 )的常亮推送至栈顶.
        //sipush 表示将一个短整形常量值(-32768 ~ 32767) 推至栈顶
        //sipush 表示将一个短整形常量值(-32768 ~ 32767) 推至栈顶
        //iconst_1 表示将int类型1  推至栈顶 (iconst_m1 ~ iconst_5) 从-1 到5
        /**
         *  public   (int i) {
         *     super(com.sun.org.apache.bcel.internal.Constants.ICONST_0, (short)1);
         *
         *     if((i >= -1) && (i <= 5))
         *       opcode = (short)(com.sun.org.apache.bcel.internal.Constants.ICONST_0 + i); // Even works for i == -1
         *     else
         *       throw new ClassGenException("ICONST can be used only for value between -1 and 5: " +
         *                                   i);
         *     value = i;
         *   }
         */
        System.out.println(MySon.str);
    }

}

class MyParent{
    public  final static int str= 10;
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
