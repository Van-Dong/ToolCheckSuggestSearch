package com.example.toolchecksuggestsearch.controller;

import java.time.YearMonth;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.toolchecksuggestsearch.entity.KeywordTracker;
import com.example.toolchecksuggestsearch.service.KeywordTrackerService;
import com.example.toolchecksuggestsearch.service.SearchResultService;
import com.example.toolchecksuggestsearch.service.SeleniumService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

// @RestController
@Controller
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {
    KeywordTrackerService keywordTrackerService;
    SearchResultService searchResultService;
    private final SeleniumService seleniumService;

    // Accept yearAndMonth from (2000-01 -> 2099-12) or ""
    @GetMapping(value = {"", "{yearAndMonth:^20[0-9][0-9](?:0[1-9]|1[0-2])$}"})
    public String getHomePage(
            @PathVariable(required = false) Integer yearAndMonth,
            @RequestParam(defaultValue = "0") Integer page,
            Model model) {
        // Validate Param
        YearMonth yearMonth =
                (yearAndMonth == null) ? YearMonth.now() : YearMonth.of(yearAndMonth / 100, yearAndMonth % 100);
        yearMonth = yearMonth.isAfter(YearMonth.now()) ? YearMonth.now() : yearMonth;
        if (page < 0) page = 0;

        int daysInMonth =
                YearMonth.of(yearMonth.getYear(), yearMonth.getMonthValue()).lengthOfMonth();
        List<YearMonth> months = searchResultService.getAllSearchedMonths();
        Page<KeywordTracker> data =
                keywordTrackerService.getAllKeywordTrackerByPage(page, yearMonth.getMonthValue(), yearMonth.getYear());

        model.addAttribute("currentMonth", yearMonth);
        model.addAttribute("daysInMonth", daysInMonth);
        model.addAttribute("months", months);
        model.addAttribute("data", data);

        return "main/main";
    }
}
