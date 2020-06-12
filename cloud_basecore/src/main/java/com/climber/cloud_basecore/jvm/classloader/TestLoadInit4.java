package com.climber.cloud_basecore.jvm.classloader;


import java.util.UUID;

public class TestLoadInit4 {
    public static void main(String[] args) {

        System.out.println(inter.num);

        Singleton singleton = Singleton.getInstance();
        System.out.println("counter1:"+Singleton.counter1);
        System.out.println("counter2:"+Singleton.counter2);
    }

}


interface  inter{
    public String num= UUID.randomUUID().toString();
    int numer2=2;
}

class outerClass implements   inter{
}

class Singleton{
  public static int counter1;

    private static Singleton singleton= new Singleton();
    private Singleton(){
        counter1++;
        counter2++;    //准备阶段的重要意义
        System.out.println(counter1);
        System.out.println(counter2);
    }

    public static int counter2=0;

    public static Singleton getInstance(){
        return singleton;
    }

}
