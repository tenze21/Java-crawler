package org.jcrawler.commands;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import org.jcrawler.repository.SqliteRepository;
import org.jcrawler.utils.UrlArrayList;

public class SearchCommand implements Command {
  private final SqliteRepository repo;

  public SearchCommand(SqliteRepository repo) {
    this.repo = repo;
  }

  @Override
  public void execute() {
    try {
      String input = "";
      try (Scanner sc = new Scanner(System.in);) {
        System.out.print("Enter keywords: ");
        input = sc.nextLine();
      } catch (Exception e) {
        System.err.println(e.getMessage());
        System.exit(1);
      }

      String[] keywords = input.trim().split("\\s+");
      UrlArrayList urls = new UrlArrayList();
      for (String token : keywords) {
        ArrayList<String> tokenURLs = repo.getUrlsByToken(token.toLowerCase());
        for (String url : tokenURLs) {
          urls.push(url);
        }
      }
      /*
       * sort the list of urls depending on the number of times they occur and print
       * the sorted list of urls
       */
      urls.sort();
      urls.print();
    } catch (SQLException e) {
      System.err.println("SQL error: " + e.getMessage());
    }
  }
}
