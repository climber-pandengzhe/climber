package com.climber.cloud_basecore.jvm.bytecode;


/**
 * 1:使用javap -verbose 命令分析一个字节码文件时,将会分析该字节码文件的魔数,版本号,常量池,类信息,类的构造方法,类中的方法信息,类变量与成员变量. -p,会把private相关信息也展示出来.
 * 2:魔数:所有的.class字节码文件的前四个字节都是魔数,魔数固定值为: 0xCAFEBABE.
 * 3:魔数之后的四个字节为版本信息,前两个字节为 minor version(次版本号),后两个字节表示major version(主版本号).这里的版本号为为00 00 00 34,换算成十进制,表示次版本号为0,主版本号为52
 * 所以该文件的版本号为1.8.0 可以通过java -version命令来验证这一点.
 * 4:常量池(constant pool):紧接着主版本之后的就是常量池,一个java类中定义的许多信息都是有常量池来维护和描述的.可以将常量池看做Class文件的资源仓库
 * 比如java类中定义的方法与变量信息,都是存储在常量池中,常量池中主要存储两类变量:字面量与符号引用.字面量如文本字符串,Java中声明为final的常量值.
 * 而符合引用如类和接口的全局限定名,字段的名称和描述符,方法的名称和描述符.
 * 5:常量池的总体结构:java类所对应的常量池主要由常量池数量和常量池数组这两部分组成,常量池数量紧跟在主版本号后面,占据两个字节;常量池数组则紧跟在常量池数量之后.
 * 常量池数组与一般数组不同的是,常量池数组中不通的元素类型,结构都是不通的,长度当然也不同;但是每一种元素的第一个数据都是u1类型,该字节是个标记为,占据一个字节.
 * jvm在解析常量池时候,会根据u1类型来获取元素的具体类型.值得注意的是,常量池中数组元素的个数=常亮池数-1(0暂时不用),目的是满足某些常量池索引值的数据在特定情况下
 * 需要表达:"不引用任何一个常量池"的含义:根本原因在于,索引为0也是一个常数(保留常量),只不过它不位于常量表中,这个常量就对应null值;所以常量池的索引从1而不是从0开始.
 *6: 在JVM规范中,每个变量/字段都有描述信息,描述信息主要的作用是描述字段的数据类型,方法的参数列表(包括数量,类型与顺序)与返回值根据描述符规则,基本数据类型和代表无返回值的
 * void类型,都用一个大写字符来表示,对象类型则使用个字符L加对象的全限定名称来表示.为了压缩字节码文件的体积,对于基本数据类型,JVM都只是用一个大写字母来表示,如下所示:
 * B-byte,C-char,D-double,F-float,I-int,J-long,S-short,Z-boolean,V-void,L-对象类型,如Ljava/lang/String;
 * 7:对于数组来说,每一个维度,使用一个前置的[来表示,如int[],被记录为[I,String [][]被记录为[[Ljava/lang/String
 * 8:用描述符描述方法时,按照先参数列表,后返回值的顺序来描述.参数列表按照严格的顺序放在一组()之内,如方法:String getRealNameByIdAndNickName
 * (int id,String name)的描述符为 (I,Ljava/lang/String;) L/java/lang/String;
 * 9:
 *
 */
public class MyTest1 {

    {
        System.out.println("asdf");
    }
    MyTest1(){
        System.out.println("a");
    }
    MyTest1(String a){
        System.out.println("a");
    }

    static {
        System.out.println("30  "+MyTest1.abc);
        MyTest1.abc="bc";
        System.out.println("32  "+MyTest1.abc);
    }
    static String abc="a";

    public static void main(String[] args) {

    }


}





