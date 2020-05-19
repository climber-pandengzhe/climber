package com.climber.cloud_basecore.juc.blockQueue0;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * 生产者和消费者案例
 */
public class TestProductorAndConsumer {

    public static void main(String[] args) {

        Clerk clerk = new Clerk();

        Productor pro = new Productor(clerk);
        Consumer cus = new Consumer(clerk);

        new Thread(pro, "生产者 A").start();
        new Thread(cus, "消费者 B").start();

        new Thread(pro, "生产者 C").start();
        new Thread(cus, "消费者 D").start();
    }
}

//店员
class Clerk{
    private int product = 0;

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    //进货
    public  void get(){//循环次数：0
        lock.lock();
        while(product >= 1){
            System.out.println("产品已满！");
            try {
                condition.await();
            } catch (InterruptedException e) {
            }
        }

        System.out.println(Thread.currentThread().getName() + " : " + ++product);
        //为避免线程不能正常关闭（一直处在wait状态未唤醒），notifyAll()方法不要放在if...else...中

        condition.signalAll();
        lock.unlock();
    }

    //卖货
    public  void sale(){//product = 0; 循环次数：0
        lock.lock();
        while(product == 0){
            System.out.println("缺货！");
            try {
                condition.await();
            } catch (InterruptedException e) {
            }
        }

        System.out.println(Thread.currentThread().getName() + " : " + --product);
        condition.signalAll();
        lock.unlock();
    }
}

//生产者
class Productor implements Runnable{
    private Clerk clerk;

    public Productor(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
            }

            clerk.get();
        }
    }
}

//消费者
class Consumer implements Runnable{
    private Clerk clerk;

    public Consumer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            clerk.sale();
        }
    }
}
