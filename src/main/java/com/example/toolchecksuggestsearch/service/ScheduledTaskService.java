package com.example.toolchecksuggestsearch.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.toolchecksuggestsearch.dto.respone.SeleniumResponse;
import com.example.toolchecksuggestsearch.entity.KeywordTracker;
import com.example.toolchecksuggestsearch.entity.SearchResult;
import com.example.toolchecksuggestsearch.entity.SuggestedKeyword;
import com.example.toolchecksuggestsearch.repository.KeywordTrackerRepository;
import com.example.toolchecksuggestsearch.repository.SearchResultRepository;
import com.example.toolchecksuggestsearch.ultils.CheckMatchingUtils;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class ScheduledTaskService {
    SeleniumService seleniumService;
    KeywordTrackerRepository keywordTrackerRepository;
    SearchResultRepository searchResultRepository;

    //    @Scheduled(initialDelay = 10000, fixedRate = 1000 * 60 * 60 * 24)
    @Scheduled(cron = "0 0 0 * * ?") // Run ats 0:00:00 AM every day
    private void keywordTrackingScreenshotTask() {
        keywordTrackerRepository.findAll().stream().forEach(kt -> {
            // capture screenshots and take keyword suggestions
            SeleniumResponse seleniumResponse = seleniumService.retrieveKeywordSuggestionsWithScreenshot(
                    kt.getSearchKeyword(), kt.getPlatform(), kt.getIsPC());
            if (seleniumResponse == null) return;

            List<String> keywordSuggestions = seleniumResponse.getSuggestedKeywords(); // searchResult in browser
            List<String> matchingKeywords =
                    List.of(kt.getMatchKeywords().toLowerCase().split(", ")); // expected keyword

            // Create object for SuggestedKeyword entity
            List<SuggestedKeyword> suggestedKeywordList = createSuggestedKeywords(keywordSuggestions);

            // Get index of searchResults match
            List<Integer> matchingIndices =
                    CheckMatchingUtils.findMatchingIndices(matchingKeywords, keywordSuggestions, kt.getIsAllMatch());

            // Mark keyword is matching
            matchingIndices.forEach(mi -> suggestedKeywordList.get(mi).setIsMatching(true));

            // Save to database
            saveSearchResult(kt, seleniumResponse.getImageURl(), suggestedKeywordList, !matchingIndices.isEmpty());
        });
    }

    private void saveSearchResult(
            KeywordTracker kt, String imageUrl, List<SuggestedKeyword> suggestedKeywordList, boolean isMatchFound) {
        // Create SearchResult
        SearchResult searchResult = SearchResult.builder()
                .urlScreenshot(imageUrl)
                .suggestedKeywords(suggestedKeywordList)
                .isMatchFound(isMatchFound)
                .keywordTracker(kt)
                .build();

        // Save to database
        searchResultRepository.save(searchResult);
    }

    private List<SuggestedKeyword> createSuggestedKeywords(List<String> keywordSuggestions) {
        List<SuggestedKeyword> suggestedKeywordList = new ArrayList<>();
        keywordSuggestions.forEach(sk -> {
            SuggestedKeyword suggestedKeyword =
                    SuggestedKeyword.builder().keyword(sk).isMatching(false).build();
            suggestedKeywordList.add(suggestedKeyword);
        });
        return suggestedKeywordList;
    }
}
