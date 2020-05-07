package com.climber.cloud_basecore.juc.volatile0;


import java.util.concurrent.atomic.AtomicInteger;

/**
 * i可能会出现总和不是10000的情况,由此可见,volatile不能保证原子性.
 * i2结果总是10000
 */
public class AtomicityTest {
    volatile int i=0;
    AtomicInteger i2= new AtomicInteger(0);

    public void add(){
        i++;
    }

    public void addAtomic(){
        i2.incrementAndGet();
    }
    public static void main(String[] args) {
        AtomicityTest test= new AtomicityTest();
        for(int j=0;j<10;j++){
            new Thread(() -> {
                for(int k=0;k<1000;k++){
                    test.add();
                    test.addAtomic();

                }
            }).start();
        }
        //需要上面全部线程执行完成后,main线程查看总数.
        if(Thread.activeCount()>2){
            Thread.yield();
        }
        System.out.println("总数为:i="+test.i);
        System.out.println("总数为:i2="+test.i2);
    }
}
