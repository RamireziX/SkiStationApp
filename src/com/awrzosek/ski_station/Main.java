package com.awrzosek.ski_station;

import com.awrzosek.ski_station.basic.BasicConsts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {
        //just to test if connection is ok
        Connection conn;
        Statement stat;
        try {
            Class.forName(BasicConsts.DRIVER);
            System.out.println("ok");
        } catch (ClassNotFoundException e) {
            System.err.println("err");
            e.printStackTrace();
        }

        try {
            conn = DriverManager.getConnection(BasicConsts.DB_URL);
            stat = conn.createStatement();
            System.out.println("ok");
        } catch (SQLException e) {
            System.err.println("err");
            e.printStackTrace();
        }
    }
}
