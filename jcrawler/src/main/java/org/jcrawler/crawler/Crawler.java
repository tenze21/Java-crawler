package org.jcrawler.crawler;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.sql.SQLException;

import org.jcrawler.repository.UrlRepository;
import org.jcrawler.utils.HashSet;
import org.jcrawler.utils.Queue;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.ValidationException;
import org.jsoup.nodes.Document;

public class Crawler implements Runnable {
    Queue<String> frontierQueue;
    Queue<Document> parserQueue;
    HashSet hashSet;
    private final UrlRepository repository;

    public Crawler(Queue<String> fq, Queue<Document> pq, HashSet hs, UrlRepository repo) {
        this.frontierQueue = fq;
        this.parserQueue = pq;
        this.hashSet = hs;
        this.repository= repo;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String url = frontierQueue.take();
                System.out.println("crawling: " + url);
                Connection.Response res= Jsoup.connect(url).execute();
                Document doc=res.parse();
                int statusCode= res.statusCode();
                String domain= doc.baseUri().split("/")[2];
                hashSet.add(url);
                parserQueue.put(doc);
                System.out.println("crawled: " + doc.location() + " Status: " + statusCode);

                repository.saveURL(url, statusCode, domain);
            } catch (UncheckedIOException | IOException e) {
                System.err.println("IoException in crawler: " + e.getMessage());
            } catch (ValidationException e) {
                System.err.println("url validation exception in crawler: " + e.getMessage());
            } catch (SQLException e){
                System.err.println("SQL error(crawler): " + e.getMessage());
            }
        }
    }
}
