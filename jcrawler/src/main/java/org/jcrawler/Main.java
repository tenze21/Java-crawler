package org.jcrawler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.jcrawler.arraylist.ArrayList;
import org.jcrawler.crawler.Crawler;
import org.jcrawler.db.Db;
import org.jcrawler.hashset.HashSet;
import org.jcrawler.parser.Parser;
import org.jcrawler.queue.Queue;
import org.jsoup.nodes.Document;

/**
 * Hello world!
 */
public class Main {
    public static void main(String[] args) {
        if(args.length!=1){
            System.err.println("usage error: No options passed. Use -h option to view program manual.");
            System.exit(1);/*terminate program*/
        }
        if(args[0].equals("-s") || args[0].equals("-search")){/*run the search program*/
            try {
                Scanner sc= new Scanner(System.in);
                System.out.print("Enter keywords: ");
                String input= sc.nextLine();
                String[] keywords= input.trim().split("\\s+");
                ArrayList urls= new ArrayList();
                for(String token: keywords){
                    Db dbConnection= new Db();
                    String query= "SELECT url FROM crawler_data WHERE token=?";
                    PreparedStatement pstmt= dbConnection.con.prepareStatement(query);
                    pstmt.setString(1, token);
                    ResultSet rs=pstmt.executeQuery();
                    rs.next();
                    while(rs.next()){
                        urls.push(rs.getString(1));
                    }
                }
                /*sort the list of urls depending on the number of times they occur and print the sorted list of urls */
            } catch (SQLException e) {
                System.err.println("SQL error: " + e.getMessage());
            }
        }else if(args[0].equals("-c") || args[0].equals("-crawl")){/*run the crawl program*/
            Scanner sc= new Scanner(System.in);
            System.out.print("Enter seed url(s): ");
            String input= sc.nextLine();
            String[] urls= input.trim().split("\\s+");

            /*Terminate program if no seed url(s) is provided*/
            if(urls.length==1 && urls[0].equals("")){
                System.err.println("Crawler error: No seed urls provided.");
                System.exit(1);
            }

            /*
            Declare a shared parser queue, frontier queue and hash set.
            */
            Queue<String> frontierQueue= new Queue<>();
            Queue<Document> parserQueue= new Queue<>();
            HashSet hashSet= new HashSet();

            /*Insert seed urls*/
            frontierQueue.insertAll(urls);

            /*define a new crawler object*/
            Crawler crawler= new Crawler(frontierQueue, parserQueue, hashSet);
            Thread crawlerThread= new Thread(crawler);
            /*define a new parser object*/
            Parser parser= new Parser(frontierQueue, parserQueue, hashSet);
            Thread parserCrawler= new Thread(parser);

            /*start the crawler and parser threads*/
            crawlerThread.start();
            parserCrawler.start();
        }else if(args[0].equals("-h") || args[0].equals("-help")){/*Show program instructions.*/
            System.out.println("Jcrawler comes with two features: to crawl the web starting from the provided seed url(s) and to search for urls with sepcific keywords. You can choose whether to run the search program or the crawl program by passing the -s and -c options respectively.");
            System.out.println("-s, -search: For running the search program. You will be prompted to enter a keyword you want to search for, not providing any keywords will throw an error. Seperate each keyword with a space e.g, Enter keywords: search this keyword");
            System.out.println("-c, -crawl: For running the crawler program. You will be prompted to enter the seed urls, not providing any seed urls will result in an error. Seperate each seed url with a space e.g, Enter seed url(s): https://example1.com https://example2.com");
            System.out.println("-h, -help: For viewing program instructions. Use if you are confused or not sure how to use jcrawler.");
        }
    }
}