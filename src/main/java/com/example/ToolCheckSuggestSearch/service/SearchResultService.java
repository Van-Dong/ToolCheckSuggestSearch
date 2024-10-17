package com.example.ToolCheckSuggestSearch.service;

import com.example.ToolCheckSuggestSearch.entity.SearchResult;
import com.example.ToolCheckSuggestSearch.repository.SearchResultRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SearchResultService {
    SearchResultRepository searchResultRepository;

    public List<YearMonth> getAllSearchedMonths() {
        SearchResult searchResult = searchResultRepository.findFirstByOrderByCreatedAtAsc().orElse(null);
        YearMonth startMonth = searchResult == null ? YearMonth.from(LocalDate.now()) :
                YearMonth.from(searchResult.getCreatedAt());
        YearMonth endMonth = YearMonth.from(LocalDate.now());

        // List YearMonth from startMonth to endMonth
        List<YearMonth> months = new ArrayList<>();
        while(!startMonth.isAfter(endMonth)) {
            months.add(startMonth);
            startMonth = startMonth.plusMonths(1);
        }

        return months;
    }
}
