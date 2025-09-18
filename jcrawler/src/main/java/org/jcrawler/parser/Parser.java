package org.jcrawler.parser;

import org.jcrawler.hashset.HashSet;
import org.jcrawler.queue.Queue;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser implements Runnable{
    Queue<String> frontierQueue;
    Queue<Document> parserQueue;
    HashSet hashSet= new HashSet();
    public Parser(Queue fq, Queue pq, HashSet hs){
        frontierQueue=fq;   
        parserQueue=pq;
        hashSet=hs;
    }
    @Override
    public void run(){
        while (true) { 
            // Connection DbConnection= Db.initializeDbConnection();
            Document doc= parserQueue.take();
            Elements links= doc.select("a");
            // Elements headers= prasedHtml.select("h1, h2, h3");
            // for(Element header: headers){
            //     String h= header.text();
            //     String[] tokens= h.split(" ");
            // }
            for(Element link: links){
                String linkHref=link.attr("abs:href");
                if(!(hashSet.contains(linkHref)) && !(linkHref.equals(" ") || linkHref.equals("")) && (linkHref.startsWith("http://") || linkHref.startsWith("https://"))){
                    frontierQueue.put(linkHref);
                }
            }
        }
    }
}