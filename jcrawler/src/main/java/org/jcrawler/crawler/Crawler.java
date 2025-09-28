package org.jcrawler.crawler;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.jcrawler.db.Db;
import org.jcrawler.hashset.HashSet;
import org.jcrawler.queue.Queue;
import org.jsoup.Connection;
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
                Connection.Response res= Jsoup.connect(url).execute();
                Document doc=res.parse();
                int statusCode= res.statusCode();
                String domain= doc.baseUri().split("/")[2];
                hashSet.add(url);
                parserQueue.put(doc);
                System.out.println("crawled: " + doc.location() + " Status: " + statusCode);

                // db interaction
                Db dbConnection= new Db();
                String insertQuery= "INSERT OR IGNORE INTO urls (url, response, domain) VALUES (?, ?, ?)";
                PreparedStatement pstmt= dbConnection.con.prepareStatement(insertQuery);
                pstmt.setString(1, url); //set url
                pstmt.setInt(2, statusCode);//set response code
                pstmt.setString(3, domain);//set domain
                pstmt.executeUpdate();
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
