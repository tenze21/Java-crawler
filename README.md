# Java-crawler
A simple web crawler in Java.

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
    .read ./src/main/java/org/jcrawler/db/initializeDb.sql
    ```

## Executing jcrawler with codehause plugin
```bash
mvn compile exec:java -Dexec.args="seed_urls"
```