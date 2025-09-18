package org.jcrawler.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db{
    Connection con;
    public Db(){
        try{
            String jdbcUrl= "jdbc:sqlite:jcrawler.db";
            con= DriverManager.getConnection(jdbcUrl);
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }
}