package com.example.toolchecksuggestsearch.ultils;

import java.util.ArrayList;
import java.util.List;

public class CheckMatchingUtils {

    private CheckMatchingUtils() {}

    // Get index of searchResults match
    public static List<Integer> findMatchingIndices(
            List<String> displayKeywords, List<String> searchResults, boolean isAllMatch) {
        List<Integer> result = new ArrayList<>();
        for (String displayKeyword : displayKeywords) {
            for (int i = 0; i < searchResults.size(); i++) {
                boolean isMatch;
                isMatch = isAllMatch
                        ? isAllMatch(displayKeyword, searchResults.get(i))
                        : isPartialMatch(displayKeyword, searchResults.get(i));
                if (isMatch) {
                    result.add(i);
                }
            }
        }
        return result;
    }

    // Check partial match between 2 string
    private static boolean isPartialMatch(String sentence1, String sentence2) {
        return sentence2.toLowerCase().contains(sentence1.toLowerCase());
    }

    // Check all match between 2 string
    private static boolean isAllMatch(String sentence1, String sentence2) {
        return sentence1.equalsIgnoreCase(sentence2);
    }
}
