# Java-crawler
> A simple web crawler written in Java.

## Architecture

### Crawler
The crawler takes URLs in sequence from the **frontier queue** and fetches the HTML content from the URL and saves it in the parser queue. The crawler also saves the fetched URL details including the URL, response, domain and the timestamp to the sqlite database and it also adds the crawled URL to the **Hashset** so that the it isn't included again in the frontier queue.

### Parser
The parser takes the HTML contents of URLS fetched and put into the **parser queue** by the crawler and parses the contents using **jsoup**. It takes the headers(the contents of HTML `h1`, `h2`, `h3` tags) from the web page content and breaks them into tokens which are stored in the sqlite database against the URL itself. It also takes the links in the page(the value of the `href` attribute of the `a` tag) and checks in the **HashSet** if the URL is present there if not the URL is saved in the **frontier queue** to be crawled nu the crawler.

### Frontier Queue
The frontier queue is a Queue data structure that stores the URLs to be crawled. The parser puts the URLs it finds in a crawled page content and puts it into the frontier queue while the crawler takes those URLs in sequence and fetches their page content.

### Parser Queue
The parser queue is also a queue data structure that stores the HTML page contents of a site parsed into the **jsoup Document class**. The crawler puts the parsed HTML documents into the parser queue while the parser takes them and extracts the headers and links.

### Database
Jcrawler uses a sqlite database to store the crawled data, the sqlite database has two tables the **urls** table and the **crawler_data** table. The **urls** table stores the details about a crawled URL, the URL, the response jcrawler received when fetching, the domain and the timestamp. The **crawler_data** table stores the tokens from a page against the URL.

### Searching
Jcrawlers search feature allows the user to look up the URLs containing specific keywords provided by the user. Jcrawler queries the Sqlite database for keywords and gets the URLs matched against those keywords, the URLs are sorted based on the number of times they appear this is because it is already known that if a user enters more than one keywords than a URL containing all those keywords will appear that many times and hence will be shown at the top.

### Concurrency
Note that the crawler and the parser aren't sequential programs but rather they run simultaneously on different threads using Java's concurrency APIs and we `synchronize` their access to the `frontier queue` and the `parser queue` to avoid race conditions.

### The producer-consumer model
The parser and the crawler play a producer-consumer role for each of the queues. For the frontier queue the parser is the producer and the crawler is the consumer, hence when the frontier queue becomes full the parser sleeps and the crawler wakes it up when it removes a URL from the queue and when it is empty the crawler sleeps and the parser wakes it up when it inserts a URL into the queue to be crawled. For the parser queue the parser is the consumer and the the crawler is the producer and follows a similar wake and sleep pattern as with the frontier queue. 

## Usage

Pass the following options to Jcrawler as a commandline argument to execute it's different parts:
- `-c` or `--crawl`: prompts to enter seed URLs and starts the crawler and the parser.
- `-s` or `--search`: prompts to enter the keywords and prints URLs containing the keywords.
- `-h` or `--help`: prints the jacrawler usage manual.

## Executing
- Download the latest jar file from Github releases.
- Save the jar file in a proper directory and execute it as:
    ```
    java -jar jcrawler-0.0.1.jar [option]
    ```
- creates a `jcrawler.db` file used by jcrawler to store and retrieve data.

### Running jcrawler from source
- Prerequisites
    - Maven

- Clone the jcrawler repository.
    ```
    git clone https://github.com/tenze21/Java-crawler.git
    ```
- Get into the `jcrawler` directory
    ```
    cd Java-crawler/jcrawler
    ```
- Execute jcrawler using codehause plugin
    ```bash
    mvn compile exec:java -Dexec.args="[option]"
    ```


>                                       Compiled by Tenzin Choda, May 2026