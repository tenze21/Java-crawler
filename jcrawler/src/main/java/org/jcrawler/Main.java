package org.jcrawler;

import org.jcrawler.commands.CommandRegistry;
import org.jcrawler.commands.CrawlCommand;
import org.jcrawler.commands.HelpCommand;
import org.jcrawler.commands.SearchCommand;
import org.jcrawler.db.SqliteDb;
import org.jcrawler.repository.SqliteRepository;
import org.jcrawler.tokenization.HeaderTokenizationStrategy;

/**
 * Hello world!
 */
public class Main {
    public static void main(String[] args) {
        SqliteRepository repo= new SqliteRepository(SqliteDb.INSTANCE.getConnection());
        HeaderTokenizationStrategy headerTokenizationStrategy= new HeaderTokenizationStrategy();

        CommandRegistry registry= new CommandRegistry();
        registry.register("-c", new CrawlCommand(repo, headerTokenizationStrategy));
        registry.register("--crawl", new CrawlCommand(repo, headerTokenizationStrategy));
        registry.register("-s", new SearchCommand(repo));
        registry.register("--search", new SearchCommand(repo));
        registry.register("-h", new HelpCommand());
        registry.register("--help", new HelpCommand());

        registry.dispatch(args[0]);
    }
}