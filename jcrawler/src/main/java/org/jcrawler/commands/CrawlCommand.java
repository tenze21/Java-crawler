package org.jcrawler.commands;

import java.util.Scanner;

import org.jcrawler.crawler.Crawler;
import org.jcrawler.utils.Queue;
import org.jsoup.nodes.Document;
import org.jcrawler.utils.HashSet;
import org.jcrawler.parser.Parser;
import org.jcrawler.repository.SqliteRepository;
import org.jcrawler.tokenization.TokenizationStrategy;

public class CrawlCommand implements Command {
  private final SqliteRepository repo;
  private final TokenizationStrategy tokenizationStrategy;

  public CrawlCommand(SqliteRepository repo, TokenizationStrategy tokenizationStrategy){
    this.repo= repo;
    this.tokenizationStrategy= tokenizationStrategy;
  }

  @Override
  public void execute() {
    String input="";
    try (Scanner sc = new Scanner(System.in)) {
      System.out.print("Enter seed url(s): ");
      input = sc.nextLine();
    } catch (Exception e) {
      System.err.println("scanner error: " + e.getMessage());
      System.exit(1);
    }
    
    String[] urls = input.trim().split("\\s+");

    /* Terminate program if no seed url(s) is provided */
    if (urls.length == 1 && urls[0].equals("")) {
      System.err.println("Crawler error: No seed urls provided.");
      System.exit(1);
    }

    /*
     * Declare a shared parser queue, frontier queue and hash set.
     */
    Queue<String> frontierQueue = new Queue<>();
    Queue<Document> parserQueue = new Queue<>();
    HashSet hashSet = new HashSet();

    /* Insert seed urls */
    frontierQueue.insertAll(urls);

    /* define a new crawler object */
    Crawler crawler = new Crawler(frontierQueue, parserQueue, hashSet, repo);
    Thread crawlerThread = new Thread(crawler);
    /* define a new parser object */
    Parser parser = new Parser(frontierQueue, parserQueue, hashSet, repo, tokenizationStrategy);
    Thread parserThread = new Thread(parser);

    /* start the crawler and parser threads */
    crawlerThread.start();
    parserThread.start();
  }
}
