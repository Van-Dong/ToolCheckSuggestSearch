package com.example.toolchecksuggestsearch.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.toolchecksuggestsearch.dto.mapstruct.KeywordTrackerMapper;
import com.example.toolchecksuggestsearch.dto.request.CreateKeywordTrackerRequest;
import com.example.toolchecksuggestsearch.entity.KeywordTracker;
import com.example.toolchecksuggestsearch.entity.SearchResult;
import com.example.toolchecksuggestsearch.repository.KeywordTrackerRepository;
import com.example.toolchecksuggestsearch.repository.SearchResultRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

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
            List<SearchResult> searchResultList =
                    searchResultRepository.findAllWithCreatedAtInMonthAndByKeywordTrackerId(
                            month, year, keywordTracker.getId());
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
