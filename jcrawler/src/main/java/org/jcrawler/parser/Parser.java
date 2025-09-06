package org.jcrawler.parser;
import org.jcrawler.queue.Queue;

public class Parser{
    Queue<String> frontierQueue= new Queue<>();
    Queue<String> parserQueue= new Queue<>();
    public Parser(Queue fq, Queue pq){
        frontierQueue=fq;
        parserQueue=pq;
    }
    public void parse(String[] args) {
        System.out.println("Hello parser");
    }
}