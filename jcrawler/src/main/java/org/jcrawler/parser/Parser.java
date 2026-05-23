package org.jcrawler.parser;

import java.sql.SQLException;

import org.jcrawler.repository.TokenRepository;
import org.jcrawler.tokenization.TokenizationStrategy;
import org.jcrawler.utils.HashSet;
import org.jcrawler.utils.IgnorableTokens;
import org.jcrawler.utils.Queue;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser implements Runnable {
    Queue<String> frontierQueue;
    Queue<Document> parserQueue;
    HashSet hashSet = new HashSet();
    private final TokenRepository repository;
    private final TokenizationStrategy tokenizationStrategy;

    public Parser(Queue<String> fq, Queue<Document> pq, HashSet hs, TokenRepository repo,
            TokenizationStrategy tokenizationStrategy) {
        this.frontierQueue = fq;
        this.parserQueue = pq;
        this.hashSet = hs;
        this.repository = repo;
        this.tokenizationStrategy = tokenizationStrategy;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Document doc = parserQueue.take();
                Elements links = doc.select("a");

                for (String token : tokenizationStrategy.tokenize(doc)) {
                    if (!IgnorableTokens.includes(token.toLowerCase())) {
                        repository.saveToken(token, doc.location());
                    }
                }
                /**
                 * @dev Dump all the links fetched from the current parsed page if all of them
                 *      cannot fit into the frontier queue.
                 *      This ensures that the crawler and the parser donot get into a deadlock
                 *      by preventing both the parser and
                 *      frontier queues from getting full at the same time.
                 */
                if (frontierQueue.availableCapacity() < links.size())
                    continue;

                for (Element link : links) {
                    String linkHref = link.attr("abs:href");
                    if (!(hashSet.contains(linkHref)) && !(linkHref.equals(" ") || linkHref.equals(""))
                            && (linkHref.startsWith("http://") || linkHref.startsWith("https://"))) {
                        frontierQueue.put(linkHref);
                    }
                }
            } catch (SQLException e) {
                System.err.println("SQL error(parser): " + e.getMessage());
            }
        }
    }
}
