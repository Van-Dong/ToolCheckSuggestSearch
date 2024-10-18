package com.example.toolchecksuggestsearch.controller;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.toolchecksuggestsearch.dto.request.CreateKeywordTrackerRequest;
import com.example.toolchecksuggestsearch.service.KeywordTrackerService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

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
