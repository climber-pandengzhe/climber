package com.climber.cloud_basecore.juc;

public class StringChangeTest {

    public void changeString(String str2){
        str2="xxx";
    }

    public static void main(String[] args) {

        StringChangeTest t = new StringChangeTest();
        String abc="abc";
        String cde= new String("cde");
        t.changeString(abc);
        t.changeString(cde);
        System.out.println(abc);
        System.out.println(cde);
    }
}
