# Java-crawler
A simple web crawler in Java that's about to become a miniature search engine.

## Initializing Sqlite database
1. Install Sqlite database.

    **Visit:** https://sqlite.org/download.html
    ```bash
    sudo apt install sqlite3
    ```

3. Get into [project root directory.](./jcrawler)
    ```bash
    sqlite3 jcrawler.db
    ```
    *Creates a new database*
4. Create tables.
    ```bash
    .read src/main/java/org/jcrawler/db/initializeDb.sql
    ```
5. Interacting with the SQLite database.

    *Write or uncomment(if already there) the SQL query you want to execute and use the `.read` SQLite command to execute the queries.*
    ```bash
    .read src/main/java/org/jcrawler/db/initializeDb.sql
    ```

## Running jcrawler with codehause plugin
```bash
mvn compile exec:java -Dexec.args="seed_urls"
```