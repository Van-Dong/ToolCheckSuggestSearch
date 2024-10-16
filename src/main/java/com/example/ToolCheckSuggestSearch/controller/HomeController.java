package com.example.ToolCheckSuggestSearch.controller;

import com.example.ToolCheckSuggestSearch.dto.respone.SeleniumResponse;
import com.example.ToolCheckSuggestSearch.entity.KeywordTracker;
import com.example.ToolCheckSuggestSearch.service.KeywordTrackerService;
import com.example.ToolCheckSuggestSearch.service.SeleniumService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;

//@RestController
@Controller
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {
    SeleniumService seleniumService;
    KeywordTrackerService keywordTrackerService;

    @GetMapping
    public String getHomePage() {
//        seleniumService.retrieveKeywordSuggestionsWithScreenshot("Viet Nam", "https://www.google.com", true);
        return "main/main";
    }

    @GetMapping("/table-data")
    @ResponseBody
    public Page<KeywordTracker> getAllKeywordTrackerByPage(@RequestParam(defaultValue = "0") Integer page,
                                                           @RequestParam(defaultValue = "10") Integer month,
                                                           @RequestParam(defaultValue = "2024") Integer year) {
        if (page < 0) page = 0;
        return keywordTrackerService.getAllKeywordTrackerByPage(page, month, year);
    }
}
