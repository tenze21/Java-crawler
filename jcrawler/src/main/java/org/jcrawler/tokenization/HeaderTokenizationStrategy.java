package org.jcrawler.tokenization;

import java.util.ArrayList;
import java.util.List;

import org.jcrawler.utils.IgnorableTokens;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class HeaderTokenizationStrategy implements TokenizationStrategy {
  @Override
  public List<String> tokenize(Document doc){
    List<String> tokens= new ArrayList<>();
    for(Element header: doc.select("h1, h2, h3")){
      for(String token: header.text().split("\\s+")){
        if(!IgnorableTokens.includes(token.toLowerCase())){
          tokens.add(token.toLowerCase());
        }
      }
    }
    return tokens;
  }
}
