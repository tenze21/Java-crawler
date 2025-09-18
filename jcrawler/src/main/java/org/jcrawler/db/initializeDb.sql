CREATE TABLE tokens(
    id INTEGER PRIMARY KEY,
    token VARCHAR(30) NOT NULL
);

CREATE TABLE urls(
    id INTEGER PRIMARY KEY,
    url VARCHAR(100) NOT NULL,
    domain VARCHAR(50) NOT NULL,
    last_crawled DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE token_url(
    token_id INT NOT NULL,
    url_id INT NOT NULL,
    PRIMARY KEY (token_id, url_id),
    FOREIGN KEY (token_id) REFERENCES tokens(token_id),
    FOREIGN KEY (URL_ID) REFERENCES urls(url_id)
);