package com.climber.cloud_basecore.jvm.classloader;

import java.sql.Driver;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @Author zhoushengqiang
 * @Date 18/6/2020 4:27 PM
 * @Version 1.0
 */
public class SPIClassLoaderTest {
    public static void main(String[] args) {
        ServiceLoader<Driver> loader = ServiceLoader.load(Driver.class);
        Iterator<Driver> iterator = loader.iterator();
        while(iterator.hasNext()){
            Driver driver = iterator.next();
            System.out.println("driver:" + driver.getClass()+",loader:"+driver.getClass().getClassLoader());
        }

    }


}
