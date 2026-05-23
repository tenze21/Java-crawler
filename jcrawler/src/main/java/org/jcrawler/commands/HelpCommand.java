package org.jcrawler.commands;

public class HelpCommand implements Command {
  @Override
  public void execute() {
    String helpGuide = "Jcrawler comes with two features: to crawl the web starting from the provided seed url(s) and to search for urls with sepcific keywords. You can choose whether to run the search program or the crawl program by passing the -s and -c options respectively.\n-s, --search: For running the search program. You will be prompted to enter a keyword you want to search for, not providing any keywords will throw an error. Seperate each keyword with a space e.g, Enter keywords: search this keyword\n-c, --crawl: For running the crawler program. You will be prompted to enter the seed urls, not providing any seed urls will result in an error. Seperate each seed url with a space e.g, Enter seed url(s): https://example1.com https://example2.com\n-h, --help: For viewing this help manual.";
    System.out.println(helpGuide);
  }
}
