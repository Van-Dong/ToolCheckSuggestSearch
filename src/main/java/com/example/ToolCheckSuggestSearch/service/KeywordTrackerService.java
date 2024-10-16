package com.example.ToolCheckSuggestSearch.service;

import com.example.ToolCheckSuggestSearch.dto.mapstruct.KeywordTrackerMapper;
import com.example.ToolCheckSuggestSearch.dto.request.CreateKeywordTrackerRequest;
import com.example.ToolCheckSuggestSearch.entity.KeywordTracker;
import com.example.ToolCheckSuggestSearch.entity.SearchResult;
import com.example.ToolCheckSuggestSearch.repository.KeywordTrackerRepository;
import com.example.ToolCheckSuggestSearch.repository.SearchResultRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class KeywordTrackerService {
    KeywordTrackerRepository keywordTrackerRepository;
    SearchResultRepository searchResultRepository;
    KeywordTrackerMapper keywordTrackerMapper;

    public Page<KeywordTracker> getAllKeywordTrackerByPage(Integer page, Integer month, Integer year) {
        Page<KeywordTracker> keywordTrackerPage = keywordTrackerRepository.findAll(PageRequest.of(page, 10));
        for (KeywordTracker keywordTracker : keywordTrackerPage.getContent()) {
            List<SearchResult> searchResultList = searchResultRepository
                    .findAllWithCreatedAtInMonthAndByKeywordTrackerId(month, year, keywordTracker.getId());
            keywordTracker.setSearchResults(searchResultList);
        }
        return keywordTrackerPage;
    }

    public void createKeywordTracker(CreateKeywordTrackerRequest request) {
        KeywordTracker keywordTracker = keywordTrackerMapper.toKeywordTracker(request);
        keywordTrackerRepository.save(keywordTracker);
    }

    public void deleteKeywordTrackerById(Long id) {
        keywordTrackerRepository.deleteById(id);
    }
}
