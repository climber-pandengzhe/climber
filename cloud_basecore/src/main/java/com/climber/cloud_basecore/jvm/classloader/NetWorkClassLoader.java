package com.climber.cloud_basecore.jvm.classloader;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class NetWorkClassLoader extends ClassLoader {
    private String classLoaderName;

    private String path;

    public void setPath(String path) {
        this.path = path;
    }

    private final String flieExtension=".class";

    public NetWorkClassLoader(String classLoaderName){
        super();//将系统类加载器当做该类加载器的父加载器
        this.classLoaderName=classLoaderName;
    }

    public NetWorkClassLoader(ClassLoader parent,String classLoaderName){
        super(parent);//显式指定该类加载器的父加载器.
        this.classLoaderName=classLoaderName;
    }

    @Override
    public String toString() {
        return "NetWorkClassLoader{" +
                "classLoaderName='" + classLoaderName + '\'' +
                ", flieExtension='" + flieExtension + '\'' +
                '}';
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
       byte[] data= this.loadClassData(name);
       return this.defineClass(name,data,0,data.length);
    }




    private byte[] loadClassData(String className){
        InputStream is= null;
        byte[] data=null;
        ByteArrayOutputStream baos=null;

        className = className.replace(".","/");


        try {
            this.classLoaderName=this.classLoaderName.replace(".","/");
            is= new FileInputStream(new File(this.path+className+this.flieExtension));
            baos= new ByteArrayOutputStream();
            int ch=0;
            while(-1 !=(ch=is.read())){
                baos.write(ch);
            }
            data= baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return  data;
    }
    // 修改加载顺序，优先自己加载，加载不了再让双新加载
//    @Override
//    public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
//        Class<?> c = null;
//        try {
//            c = findClass(name);
//        } catch (Exception e) {
//        }
//        return c == null ? c = super.loadClass(name, resolve) : c;
//    }

    public static void main(String[] args)throws  Exception {

        Map map  = new HashMap();

        Object o= new Object();


        map.put(o,"12q");
        map.put("1234","12q");



        System.out.println(map.keySet());

        o=null;

        System.out.println(map.keySet());

    }
}
