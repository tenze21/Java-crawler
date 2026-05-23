package org.jcrawler.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;


public class SqliteRepository implements UrlRepository, TokenRepository {
  private final Connection connection;

  public SqliteRepository(Connection con){
    this.connection= con;
  }

  @Override
  public void saveURL(String url, int statusCode, String domain) throws SQLException{
    String sql= "INSERT OR IGNORE INTO urls (url, response, domain) VALUES (?, ?, ?)";
    try (PreparedStatement pstmt= connection.prepareStatement(sql)) {
      pstmt.setString(1, url);
      pstmt.setInt(2, statusCode);
      pstmt.setString(3, domain);
      pstmt.executeUpdate();
    }   
  }

  @Override
  public void saveToken(String token, String url) throws SQLException{
    String sql= "INSERT OR IGNORE INTO crawler_data (token, url) VALUES (?, ?)";
    try(PreparedStatement pstmt= connection.prepareStatement(sql)){
      pstmt.setString(1, token);
      pstmt.setString(2, url);
      pstmt.executeUpdate();
    }
  }

  @Override
  public ArrayList<String> getUrlsByToken(String token) throws SQLException{
    String sql= "SELECT url FROM crawler_data WHERE token=?";
    ArrayList<String> urls= new ArrayList<>();

    try(PreparedStatement pstmt= connection.prepareStatement(sql)){
      pstmt.setString(1, token);
      ResultSet rs= pstmt.executeQuery();
      while(rs.next()){
        urls.add(rs.getString(1));
      }
    }
    return urls;
  }
}
