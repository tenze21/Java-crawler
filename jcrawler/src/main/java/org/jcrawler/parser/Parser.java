package org.jcrawler.parser;

import org.jcrawler.hashset.HashSet;
import org.jcrawler.queue.Queue;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser implements Runnable{
    Queue<String> frontierQueue= new Queue<>();
    Queue<String> parserQueue= new Queue<>();
    HashSet hashSet= new HashSet();
    public Parser(Queue fq, Queue pq, HashSet hs){
        frontierQueue=fq;   
        parserQueue=pq;
        hashSet=hs;
    }
    @Override
    public void run(){
        while (true) { 
            String html= parserQueue.take();
            Document prasedHtml=Jsoup.parse(html);
            Elements links= prasedHtml.select("a");
            for(Element link: links){
                String linkHref=link.attr("abs:href");
                if(!(hashSet.contains(linkHref)) && !(linkHref.equals(" ") || linkHref.equals("")) && (linkHref.startsWith("http://") || linkHref.startsWith("https://"))){
                    frontierQueue.put(linkHref);
                }
            }
        }
    }
}