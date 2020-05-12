package com.climber.cloud_basecore.juc.volatile0;

import java.util.concurrent.TimeUnit;

/**
 * 如果i 没有用volatile 修饰,那么 while()循环这里就被永远停在这里,执行不了.
 */
public class VisibleTest {

    volatile int i=0;

    public void update(){
        i=100;
    }

    public static void main(String[] args) {
        VisibleTest test= new VisibleTest();
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            test.update();
            System.out.println("更改数据i:"+test.i);
        }).start();
        while(test.i==0){

        }
        System.out.println("更改后的数据i="+test.i);
    }
}


