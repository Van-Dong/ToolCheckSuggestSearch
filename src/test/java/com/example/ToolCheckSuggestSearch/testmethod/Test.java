package com.example.ToolCheckSuggestSearch.testmethod;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Test {
    public static void main(String[] args) {
        LocalDate startDate = LocalDate.now(); // Thay thế bằng ngày bắt đầu bạn chỉ định
        LocalDate currentDate = LocalDate.now();

        // Tạo danh sách các tháng từ startDate đến hiện tại
        List<YearMonth> months = new ArrayList<>();
        YearMonth startMonth = YearMonth.from(startDate);
        YearMonth currentMonth = YearMonth.from(currentDate);


        while (!startMonth.isAfter(currentMonth)) {
            months.add(startMonth);
            startMonth = startMonth.plusMonths(1);  // Chuyển sang tháng tiếp theo
        }
        System.out.println(YearMonth.of(2024,9).toString().replace("-", ""));
    }


    // Get index of searchResults match partial
    private static List<Integer> allMatch(List<String> displayKeywords, List<String> searchResults) {
        List<Integer> result = new ArrayList<>();
        for (String displayKeyword : displayKeywords) {
            for (int i = 0; i < searchResults.size(); i++) {
                if (isAllMatch(displayKeyword, searchResults.get(i))) {
                    result.add(i);
                }
            }
        }
        return result;
    }

    // Get index of searchResults match all
    private static List<Integer> partialMatch(List<String> displayKeywords, List<String> searchResults) {
        List<Integer> result = new ArrayList<>();
        for (String displayKeyword : displayKeywords) {
            for (int i = 0; i < searchResults.size(); i++) {
                if (isPartialMatch(displayKeyword, searchResults.get(i))) {
                    result.add(i);
                }
            }
        }
        return result;
    }

    // Check partial match between 2 string
    private static boolean isPartialMatch(String sentence1, String sentence2) {
        String[] words1 = sentence1.toLowerCase().split("\\s+");
        Set<String> wordSet2 = new HashSet<>(List.of(sentence2.toLowerCase().split("\\s+")));

        for (String word : words1) {
            if (wordSet2.contains(word)) {
                return true;
            }
        }
        return false;
    }

    // Check all match between 2 string
    private static boolean isAllMatch(String sentence1, String sentence2) {
        Set<String> wordSet1 = new HashSet<>(List.of(sentence1.toLowerCase().split("\\s+")));
        Set<String> wordSet2 = new HashSet<>(List.of(sentence2.toLowerCase().split("\\s+")));

        return wordSet1.containsAll(wordSet2) || wordSet2.containsAll(wordSet1);
    }
}
