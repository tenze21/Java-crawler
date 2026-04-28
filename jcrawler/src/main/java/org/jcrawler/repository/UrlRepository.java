package org.jcrawler.repository;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UrlRepository {
  void saveURL(String url, int statusCode, String domain) throws SQLException;
  ArrayList<String> getUrlsByToken(String token) throws SQLException;
}
