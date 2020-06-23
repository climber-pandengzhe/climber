package com.climber.cloud_basecore.jvm.classloader;

/**
 * @Author zhoushengqiang
 * @Date 18/6/2020 3:20 PM
 * @Version 1.0
 *
 * 当前类加载器(Current Classloader)
 * 每个类都会使用自己的类加载器(即加载自身的的类加载器)来去加载其他类(指的是所依赖的类)
 * 如果ClassX引用了ClassY,那么ClassX的类加载器就会去加载ClassY(前提是ClassY尚未被加载)
 * 线程上下文加载器(Context Classloaser)
 *
 * 线程上下文加载器从JDK1.2开始引入,类Thread中的getContextClassLoader和setContextClassLoader(Classloaser cl)
 * 分别用来获取和设置上线文类加载器.
 *
 * 如果没有通过setContextClassLoader(Classloaser cl)进行设置的话,线程将会继承其父线程的上下文类加载器.
 * Java应用运行时的初始线程的上下文类加载器是系统类加载器,在线程中运行的代码可以通过该类加载器来加载类与资源.
 *
 * 线程上下文类加载器的重要性:
 *
 * SPI(Service provide Interface)
 *
 * 父ClassLoader可以使用当前线程  Thread.currentThread().getContextClassLoader() 所指定的classloader加载的类.
 * 这就改变了 父ClassLoader不能使用子ClassLoader或是其他没有直接父子关系的classLoader所加载的类的情况.即改变了双亲委托模型.
 *
 * 线程上下文类加载器就是当前线程的Current ClassLoader
 *
 * 在双亲委托模型下,类加载是由下向上的,即下层的类加载器会委托上层进行健在,但是对于SPI来说,有些接口是Java核心库锁提供的,
 * 而java核心库是启动类加载器来加载的,这些接口的上线却是来自不懂的jar包(厂商提供),java的启动类加载器是不会加载其他来源
 * 的jar包,这样传统的双亲委托模型就无法满足SPI的要求,而通过给当前线程设置上线文类加载器,就可以由设置的上下文类加载器来实现
 * 对于接口实现类的加载.
 *
 *
 *
 * 线程上线文类加载器的一般使用模式:(获取-使用-还原)
 * ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
 * try{
 *      Thread.currentThread().setContextClassLoader(targetTccl);
 *      myMethod();
 * }finally{
 *      Thread.currentThread().setContextClassLoader(classLoader)
 * }
 * myMethod里面调用了Thread.currentThread().getContextClassLoader(),获取当前线程的上线文类加载器做某些事情
 * ContextClassLoader的作用就是为了破坏java的类加载委托机制.
 *
 * 当高层提供了统一的接口让低层去实现,同时又要在高层加载(或实例化)低层的类时,就必须要通过线程上线文类加载器来帮助高层的ClassLoader找到并加载该类.
 *
 */
public class ContextClassLoaderTest implements Runnable{


    private Thread thread;

    public ContextClassLoaderTest(){
        thread= new Thread(this);
        thread.start();
    }



    @Override
    public void run() {
        ClassLoader classLoader = this.thread.getContextClassLoader();
        this.thread.setContextClassLoader(classLoader);

        System.out.println("Class: "+classLoader.getClass());
        System.out.println("parent: "+classLoader.getParent().getClass());

    }

    public static void main(String[] args) {
        new ContextClassLoaderTest();
    }

}
