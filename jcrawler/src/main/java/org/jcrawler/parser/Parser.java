package org.jcrawler.parser;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.jcrawler.db.Db;
import org.jcrawler.hashset.HashSet;
import org.jcrawler.queue.Queue;
import org.jcrawler.utils.IgnorableTokens;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser implements Runnable {

    Queue<String> frontierQueue;
    Queue<Document> parserQueue;
    HashSet hashSet = new HashSet();

    public Parser(Queue fq, Queue pq, HashSet hs) {
        frontierQueue = fq;
        parserQueue = pq;
        hashSet = hs;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Document doc = parserQueue.take();
                Elements links = doc.select("a");
                Elements headers = doc.select("h1, h2, h3");
                Db dbConnection= new Db();
                String insertQuery= "INSERT OR IGNORE INTO crawler_data (token, url) VALUES (?, ?)";
                PreparedStatement pstmt= dbConnection.con.prepareStatement(insertQuery);

                for (Element header : headers) {
                    String h = header.text();
                    String[] tokens = h.split(" ");
                    for(String token: tokens){
                        if(!IgnorableTokens.includes(token)){
                            pstmt.setString(1, token);
                            pstmt.setString(2, doc.location());
                            pstmt.executeUpdate();
                        }
                    }
                }

                for (Element link : links) {
                    String linkHref = link.attr("abs:href");
                    if (!(hashSet.contains(linkHref)) && !(linkHref.equals(" ") || linkHref.equals("")) && (linkHref.startsWith("http://") || linkHref.startsWith("https://"))) {
                        frontierQueue.put(linkHref);
                    }
                }
            } catch (SQLException e) {
                System.err.println("SQL error(parser): " + e.getMessage());
            }
                
        }
    }
}
