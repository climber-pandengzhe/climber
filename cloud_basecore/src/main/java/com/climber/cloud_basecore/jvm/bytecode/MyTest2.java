package com.climber.cloud_basecore.jvm.bytecode;

/**
 * 方法的动态分派:
 *  方法的动态分派牵扯到一个重要概念:方法接受者
 *  invokevirtual字节码置顶的多态查找流程
 *  比较方法重载(overload)与方法重写(overwrite),我们可以得到以下结论
 *  方法重载是静态的,是编译器行为,方法重写是动态的,是运行期行为.
 *
 *  针对于方法调用动态分派的过程,虚拟机会在类的方法区建立一个虚方法表的数据结构(virtual method table,vtable)
 *  针对于invokeinterface指令来说,虚拟机会建立一个接口方法表的数据结构(interface method table,itable)
 *
 *
 */
public class MyTest2 {
    public static void main(String[] args) {
        Fruit apple = new Apple();
        Fruit orange= new Orange();
        apple.test();
        orange.test();

        apple = new Orange();
        apple.test();

    }
}

class Fruit{
    public void test(){
        System.out.println("Fruit");
    }
}


class  Apple extends Fruit{
    public void test(){
        System.out.println("Apple");
    }
}



class  Orange extends Fruit{
    public void test(){
        System.out.println("Orange");
    }
}
