package org.jcrawler.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public enum SqliteDb{
    INSTANCE;
    private final Connection connection;
    private SqliteDb(){
        Connection temp= null;
        try{
            temp= DriverManager.getConnection("jdbc:sqlite:jcrawler.db");
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
        connection= temp;
    }

    public Connection getConnection() {
        return connection;
    }
}