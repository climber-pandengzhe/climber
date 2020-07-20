package com.climber.cloud_basecore.jvm.classloader;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCTest {
    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection  connection= DriverManager.getConnection("","userName","password");

    }
}
