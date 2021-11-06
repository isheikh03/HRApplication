/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author IBRAHIM SHEIKH
 */
public class DbConnection {

    public static void main(String[] args) throws Exception {
        DbConnection dbCon = new DbConnection();
        System.out.println(dbCon.getConnection());
    }

    public Connection getConnection() throws Exception {

        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hr_application", "root", "root");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }
}
