-- # ACTIVE TABLES
-- CREATE TABLE crawler_data(
--     id INTEGER PRIMARY KEY AUTOINCREMENT,
--     token VARCHAR(30) NOT NULL,
--     url VARCHAR(100) NOT NULL,
--     UNIQUE(token, url)
-- );

-- CREATE TABLE urls(
--     id INTEGER PRIMARY KEY AUTOINCREMENT,
--     url VARCHAR(100) NOT NULL UNIQUE,
--     response INT NOT NULL,
--     domain VARCHAR(50) NOT NULL,
--     crawled_on DATETIME DEFAULT CURRENT_TIMESTAMP 
-- );

-- # DRAFT TABLES
-- CREATE TABLE tokens(
--     id INTEGER PRIMARY KEY AUTOINCREMENT,
--     token VARCHAR(30) NOT NULL UNIQUE
-- );

-- CREATE TABLE urls(
--     id INTEGER PRIMARY KEY AUTOINCREMENT,
--     url VARCHAR(100) NOT NULL UNIQUE,
--     domain VARCHAR(50) NOT NULL,
--     last_crawled DATETIME DEFAULT CURRENT_TIMESTAMP
-- );

-- CREATE TABLE token_url(
--     token_id INT NOT NULL,
--     url_id INT NOT NULL,
--     PRIMARY KEY (token_id, url_id),
--     FOREIGN KEY (token_id) REFERENCES tokens(token_id),
--     FOREIGN KEY (URL_ID) REFERENCES urls(url_id)
-- );


-- DROP TABLE crawler_data;
-- DROP TABLE tokens;
-- DROP TABLE urls;
-- DROP TABLE token_url;

SELECT * FROM crawler_data;
-- SELECT * FROM urls;
-- SELECT * FROM tokens;
-- DELETE FROM urls;
-- DELETE FROM tokens;
-- DELETE FROM crawler_data;
-- SELECT url FROM crawler_data WHERE token="Footer";