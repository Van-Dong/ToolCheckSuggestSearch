package com.example.ToolCheckSuggestSearch.service;

import com.example.ToolCheckSuggestSearch.dto.respone.SeleniumResponse;
import com.example.ToolCheckSuggestSearch.entity.SearchResult;
import com.example.ToolCheckSuggestSearch.entity.SuggestedKeyword;
import com.example.ToolCheckSuggestSearch.repository.KeywordTrackerRepository;
import com.example.ToolCheckSuggestSearch.repository.SearchResultRepository;
import com.example.ToolCheckSuggestSearch.ultils.CheckMatchingUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class ScheduledTaskService {
    SeleniumService seleniumService;
    KeywordTrackerRepository keywordTrackerRepository;
    SearchResultRepository searchResultRepository;

//    @Scheduled(initialDelay = 10000, fixedRate = 1000 * 60 * 60 * 24)
    private void  keywordTrackingScreenshotTask() {
        keywordTrackerRepository.findAll().stream().forEach(kt -> {
            // capture screenshots and take keyword suggestions
            SeleniumResponse seleniumResponse = seleniumService.
                    retrieveKeywordSuggestionsWithScreenshot(kt.getSearchKeyword(), kt.getPlatform(), kt.getIsPC());

            List<String> keywordSuggestions = seleniumResponse.getSuggestedKeywords(); // keyword if result of search
            List<String> matchingKeywords = List.of(kt.getMatchKeywords().toLowerCase().split(", "));  // expected keyword

            // Get index of searchResults match
            List<Integer> matchingIndices = CheckMatchingUtils.findMatchingIndices(matchingKeywords, keywordSuggestions, kt.getIsAllMatch());

            // Create Suggested Keyword
            List<SuggestedKeyword> suggestedKeywordList = new ArrayList<>();
            keywordSuggestions.forEach(sk -> {
                SuggestedKeyword suggestedKeyword = SuggestedKeyword.builder()
                        .keyword(sk)
                        .isMatching(false)
                        .build();
                suggestedKeywordList.add(suggestedKeyword);
            });

            // Mark keyword is matching
            matchingIndices.forEach(mi -> {
                suggestedKeywordList.get(mi).setIsMatching(true);
            });

            // Create SearchResult
            SearchResult searchResult = SearchResult.builder()
                    .urlScreenshot(seleniumResponse.getImageURl())
                    .suggestedKeywords(suggestedKeywordList)
                    .keywordTracker(kt)
                    .build();

            // Save to database
            searchResultRepository.save(searchResult);
        });
    }
}
