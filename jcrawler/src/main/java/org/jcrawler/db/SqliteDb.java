package org.jcrawler.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public enum SqliteDb {
    INSTANCE;
    private final Connection connection;

    private SqliteDb() {
        Connection temp = null;
        try {
            temp = DriverManager.getConnection("jdbc:sqlite:jcrawler.db");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        connection = temp;
        initSchema();
    }

    private void initSchema() {
        String[] tables = {
            """
            CREATE TABLE IF NOT EXISTS crawler_data (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                token VARCHAR(30) NOT NULL,
                url VARCHAR(100) NOT NULL,
                UNIQUE(token, url)
            )
            """,
            """
            CREATE TABLE IF NOT EXISTS urls(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                url VARCHAR(100) NOT NULL UNIQUE,
                response INT NOT NULL,
                domain VARCHAR(50) NOT NULL,
                crawled_on DATETIME DEFAULT CURRENT_TIMESTAMP 
            );
            """
        };

        try(Statement stmt = connection.createStatement()){
            for(String ddl : tables){
                stmt.execute(ddl);
            }
        }catch (SQLException e){
            System.err.println("Schema init error: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
