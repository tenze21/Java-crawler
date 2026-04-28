package org.jcrawler.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class IgnorableTokens {

    private IgnorableTokens() {}

    private static final String[] ignorableTokensArray =
    {
        "a", "an", "the", "and", "or", "but", "if", "then", "else", "is",
        "am", "was", "are", "were", "be", "seen", "being", "been", "has", "have",
        "had", "do", "does", "did", "of", "in", "on", "at", "by", "with",
        "about", "against", "to", "from", "for", "into", "over", "under", "as",
        "because", "while", "during", "before", "after", "above", "below", "this", "that", "these",
        "those", "he", "she", "it", "they", "we", "you", "i", "me", "him",
        "her", "them", "us", "my", "your", "his", "their", "our", "no",
        "not", "so", "too", "very", "just", "only", "can", "will", "would",
        "should", "could", "may", "might", "must", "shall", "also", "see", "ohh", "wow", "hurray"
    };

    private static final Set<String> IGNORABLE_TOKENS =
        Collections.unmodifiableSet(new HashSet<>(Arrays.asList(ignorableTokensArray)));

    public static boolean includes(String item) {
        return IGNORABLE_TOKENS.contains(item);
    }
}
