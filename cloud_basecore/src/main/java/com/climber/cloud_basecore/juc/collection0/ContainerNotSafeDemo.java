package com.climber.cloud_basecore.juc.collection0;


import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ContainerNotSafeDemo {
    public static void main(String[] args) {
        List<String> list2 =  Collections.synchronizedList(new ArrayList<>());

        list2.get(3);
        CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();

        copyOnWriteArrayList.get(3);


        copyOnWriteArrayList.add("123");
        list2.add("1234");
        List<String> list = new ArrayList<>();
        for (int i=0;i<=10;i++){
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            }).start();
        }


    }
}
