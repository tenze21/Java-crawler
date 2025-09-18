package org.jcrawler.crawler;

import java.io.IOException;
import java.io.UncheckedIOException;

import org.jcrawler.hashset.HashSet;
import org.jcrawler.queue.Queue;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.ValidationException;

public class Crawler implements Runnable {

    Queue<String> frontierQueue = new Queue<>();
    Queue<String> parserQueue = new Queue<>();
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
                // System.out.println(url);
                Connection.Response res = Jsoup.connect(url).execute();
                hashSet.add(url);
                String body = res.readBody();
                parserQueue.put(body);
                System.out.println("crawled: " + res.url() + " Status code: " + res.statusCode());
            } catch (UncheckedIOException | IOException e) {
                System.err.println("IoException in crawler: " + e.getMessage());
            } catch (ValidationException e) {
                System.err.println("url validation exception in crawler: " + e.getMessage());
            }
        }
    }
}
