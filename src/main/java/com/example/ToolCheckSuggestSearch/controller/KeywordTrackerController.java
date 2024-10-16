package com.example.ToolCheckSuggestSearch.controller;

import com.example.ToolCheckSuggestSearch.dto.request.CreateKeywordTrackerRequest;
import com.example.ToolCheckSuggestSearch.service.KeywordTrackerService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/keyword-tracker")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class KeywordTrackerController {
    KeywordTrackerService keywordTrackerService;

    @PostMapping("create")
    public ResponseEntity<Void> createKeywordTracker(@Valid @ModelAttribute CreateKeywordTrackerRequest request) {
        keywordTrackerService.createKeywordTracker(request);
        return ResponseEntity.ok().build();
    }
}
