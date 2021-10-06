package com.company;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {

    private static String database = "java_test_db";

    private static String user = "user";

    private static String password = "password";

    public static Connection getConnection() throws Exception {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database, user, password);

        return con;
    }
}
