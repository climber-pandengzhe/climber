package com.climber.cloud_basecore.jvm.classloader;


import java.util.UUID;

public class TestLoadInit5 {
    public static void main(String[] args) throws ClassNotFoundException {

       Class<?> clazz= Class.forName("java.lang.String");
        System.out.println(clazz);
        Class<?> clazz2= Class.forName("com.climber.cloud_basecore.jvm.classloader.C");
        System.out.println(clazz2.getClassLoader());


        TestLoadInit5[] integers= new TestLoadInit5[2];
        System.out.println(integers.getClass().getClassLoader());

    }

}

class C{

}
