package org.jcrawler;

import org.jcrawler.crawler.Crawler;
import org.jcrawler.hashset.HashSet;
import org.jcrawler.parser.Parser;
import org.jcrawler.queue.Queue;

/**
 * Hello world!
 */
public class Main {
    public static void main(String[] args) {
        /*
        Declare a shared parser queue, frontier queue and hash set.
        */
        Queue<String> frontierQueue= new Queue<>();
        Queue<String> parserQueue= new Queue<>();
        HashSet hashSet= new HashSet();

        /*Insert seed urls*/
        frontierQueue.insertAll(args);

        /*define a new crawler object*/
        Crawler crawler= new Crawler(frontierQueue, parserQueue, hashSet);
        Thread crawlerThread= new Thread(crawler);
        /*define a new parser object*/
        Parser parser= new Parser(frontierQueue, parserQueue, hashSet);
        Thread parserCrawler= new Thread(parser);

        /*start the crawler and parser threads*/
        crawlerThread.start();
        parserCrawler.start();
    }
}
