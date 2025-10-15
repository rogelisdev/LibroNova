package com.codeup.libronova.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    private static final String URL = "jdbc:mysql://localhost:3306/libronova";
    private  static final  String USER = "rogelisdev";
    private static final String PASS = "Rogelis123*";

    public static Connection getConnection()throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
