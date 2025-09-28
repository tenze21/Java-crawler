package org.jcrawler.utils;

public class IgnorableTokens {

    static String[] ignorableTokens = 
    {
        "a", "an", "the", "and", "or", "but", "if", "then", "else", "is", 
        "am", "was", "are", "were", "be", "seen", "being", "been", "has", "have", 
        "had", "do", "does", "did", "of", "in", "on", "at", "by", "with", 
        "about", "against", "to", "from", "for", "into", "over", "under", "as", 
        "because", "while", "during", "before", "after", "above", "below", "this", "that", "these", 
        "those", "he", "she", "it", "they", "we", "you", "i", "me", "him", 
        "her", "them", "us", "my", "your", "his", "their", "our", "no", 
        "not", "so", "too", "very", "just", "only", "can", "will", "would", 
        "should", "could", "may", "might", "must", "shall", "also", "see"
    };

    public static boolean includes(String item) {
        for (String ignorableToken : ignorableTokens) {
            if (item.equals(ignorableToken)) {
                return true;
            }
        }
        return false;
    }
}
