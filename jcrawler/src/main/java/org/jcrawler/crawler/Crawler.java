package org.jcrawler.crawler;

import java.io.IOException;
import java.io.UncheckedIOException;

import org.jcrawler.hashset.HashSet;
import org.jcrawler.queue.Queue;
import org.jsoup.Jsoup;
import org.jsoup.helper.ValidationException;
import org.jsoup.nodes.Document;

public class Crawler implements Runnable {

    Queue<String> frontierQueue;
    Queue<Document> parserQueue;
    HashSet hashSet = new HashSet();

    public Crawler(Queue fq, Queue pq, HashSet hs) {
        frontierQueue = fq;
        parserQueue = pq;
        hashSet = hs;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String url = frontierQueue.take();
                System.out.println("crawling: " + url);
                Document doc = Jsoup.connect(url).get();
                hashSet.add(url);
                parserQueue.put(doc);
                System.out.println("crawled: " + doc.location());
            } catch (UncheckedIOException | IOException e) {
                System.err.println("IoException in crawler: " + e.getMessage());
            } catch (ValidationException e) {
                System.err.println("url validation exception in crawler: " + e.getMessage());
            }
        }
    }
}
