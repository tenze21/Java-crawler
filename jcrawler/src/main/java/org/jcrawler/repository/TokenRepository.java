package org.jcrawler.repository;

import java.sql.SQLException;

public interface TokenRepository {
    void saveToken(String token, String url) throws SQLException;
}
