package  org.jcrawler.crawler;

import org.jcrawler.queue.Queue;

public class Crawler{
    Queue<String> frontierQueue= new Queue<>();
    Queue<String> parserQueue= new Queue<>();
    public Crawler(Queue fq, Queue pq){
        frontierQueue=fq;
        parserQueue=pq;
    }
    public void crawl(String[] args) {
        System.out.println("Hello crawler");
    }
}