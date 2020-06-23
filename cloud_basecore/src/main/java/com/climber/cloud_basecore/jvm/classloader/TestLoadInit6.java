package com.climber.cloud_basecore.jvm.classloader;


public class TestLoadInit6 {
    public static void main(String[] args) throws ClassNotFoundException {
        System.out.println(FinalTest.x);

    }

}

class FinalTest{
    public static final  int x=3;
    static{
        System.out.println("FinalTest static block");
    }
}
