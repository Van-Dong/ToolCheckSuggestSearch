package com.example.toolchecksuggestsearch.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.toolchecksuggestsearch.entity.SearchResult;
import com.example.toolchecksuggestsearch.repository.SearchResultRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SearchResultService {
    SearchResultRepository searchResultRepository;

    public List<YearMonth> getAllSearchedMonths() {
        SearchResult searchResult =
                searchResultRepository.findFirstByOrderByCreatedAtAsc().orElse(null);
        YearMonth startMonth =
                searchResult == null ? YearMonth.from(LocalDate.now()) : YearMonth.from(searchResult.getCreatedAt());
        YearMonth endMonth = YearMonth.from(LocalDate.now());

        // Create List YearMonth from startMonth to endMonth
        // Use For Select Date in Front-End
        List<YearMonth> months = new ArrayList<>();
        while (!startMonth.isAfter(endMonth)) {
            months.add(startMonth);
            startMonth = startMonth.plusMonths(1);
        }

        return months;
    }
}
