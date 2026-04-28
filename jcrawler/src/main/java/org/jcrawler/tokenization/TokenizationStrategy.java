package org.jcrawler.tokenization;

import java.util.List;

import org.jsoup.nodes.Document;

public interface TokenizationStrategy {
  List<String> tokenize(Document doc);
}
