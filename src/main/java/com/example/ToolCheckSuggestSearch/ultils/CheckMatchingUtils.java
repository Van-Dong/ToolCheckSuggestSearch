package com.example.ToolCheckSuggestSearch.ultils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CheckMatchingUtils {

    // Get index of searchResults match
    public static List<Integer> findMatchingIndices(List<String> displayKeywords, List<String> searchResults, Boolean isAllMatch) {
        List<Integer> result = new ArrayList<>();
        for (String displayKeyword : displayKeywords) {
            for (int i = 0; i < searchResults.size(); i++) {
                boolean isMatch = false;
                isMatch = isAllMatch ? isAllMatch(displayKeyword, searchResults.get(i)) : isPartialMatch(displayKeyword, searchResults.get(i));
                if (isMatch) {
                    result.add(i);
                }
            }
        }
        return result;
    }

//    // Get index of searchResults match all
//    public static List<Integer> partialMatch(List<String> displayKeywords, List<String> searchResults) {
//        List<Integer> result = new ArrayList<>();
//        for (String displayKeyword : displayKeywords) {
//            for (int i = 0; i < searchResults.size(); i++) {
//                if (isAllMatch(displayKeyword, searchResults.get(i))) {
//                    result.add(i);
//                }
//            }
//        }
//        return result;
//    }

    // Check partial match between 2 string
    private static boolean isPartialMatch(String sentence1, String sentence2) {
        return sentence2.toLowerCase().contains(sentence1.toLowerCase());
//        Set<String> wordSet2 = new HashSet<>(List.of(sentence2.toLowerCase().split("\\s+")));
//        String[] words1 = sentence1.toLowerCase().split("\\s+");
//
//        for (String word : words1) {
//            if (wordSet2.contains(word)) {
//                return true;
//            }
//        }
//        return false;
    }

    // Check all match between 2 string
    private static boolean isAllMatch(String sentence1, String sentence2) {
//        Set<String> wordSet1 = new HashSet<>(List.of(sentence1.toLowerCase().split("\\s+")));
//        Set<String> wordSet2 = new HashSet<>(List.of(sentence2.toLowerCase().split("\\s+")));

        return sentence1.equalsIgnoreCase(sentence2);
    }
}
