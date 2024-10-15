package com.example.ToolCheckSuggestSearch.controller;

import com.example.ToolCheckSuggestSearch.service.SeleniumService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SeleniumController {
    SeleniumService seleniumService;

    @GetMapping("/fetch-title")
    public String fetchTitle(@RequestParam String url) {
        return seleniumService.fetchData(url);
    }
}
