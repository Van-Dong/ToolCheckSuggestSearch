package com.example.ToolCheckSuggestSearch.service;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

@Service
public class SeleniumService {
    public String fetchData(String url) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        String baseUrl = "https://www.google.com";
        String baseUrl2 = "https://www.yahoo.com";

        try {
            driver.get(url);

            WebElement searchInput = driver.findElement(By.cssSelector("textarea[name='q']"));
            searchInput.sendKeys("ceo google ");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("ul.G43f7e li"), 0));

            // store file
            Path screenshotStorageFolder = Paths.get("screenshots");

            if (!Files.exists(screenshotStorageFolder)) {
                Files.createDirectories(screenshotStorageFolder);
            }

            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Path destination = screenshotStorageFolder.resolve(Instant.now().getEpochSecond() + "_screenshot.png");
            Files.copy(screenshot.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);

            // Lấy tất cả các gợi ý tìm kiếm
            List<WebElement> suggestions = driver.findElements(By.cssSelector("ul.G43f7e li")); // CSS selector cho các gợi ý

            // In ra các gợi ý
            for (WebElement suggestion : suggestions) {
                System.out.println(suggestion.getText() + ", "); // In ra nội dung của gợi ý
            }
            return searchInput.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
//            driver.quit();
        }
    }


    // Get index of searchResults match partial
    private List<Integer> allMatch(List<String> displayKeywords, List<String> searchResults) {
        List<Integer> result = new ArrayList<>();
        for (String displayKeyword : displayKeywords) {
            for (int i = 0; i < searchResults.size(); i++) {
                if (isPartialMatch(displayKeyword, searchResults.get(i))) {
                    result.add(i);
                }
            }
        }
        return result;
    }

    // Get index of searchResults match all
    private List<Integer> partialMatch(List<String> displayKeywords, List<String> searchResults) {
        List<Integer> result = new ArrayList<>();
        for (String displayKeyword : displayKeywords) {
            for (int i = 0; i < searchResults.size(); i++) {
                if (isAllMatch(displayKeyword, searchResults.get(i))) {
                    result.add(i);
                }
            }
        }
        return result;
    }

    // Check partial match between 2 string
    private boolean isPartialMatch(String sentence1, String sentence2) {
        Set<String> wordSet2 = new HashSet<>(List.of(sentence2.toLowerCase().split("\\s+")));
        String[] words1 = sentence1.toLowerCase().split("\\s+");

        for (String word : words1) {
            if (wordSet2.contains(word)) {
                return true;
            }
        }
        return false;
    }

    // Check all match between 2 string
    private boolean isAllMatch(String sentence1, String sentence2) {
        Set<String> wordSet1 = new HashSet<>(List.of(sentence1.toLowerCase().split("\\s+")));
        Set<String> wordSet2 = new HashSet<>(List.of(sentence2.toLowerCase().split("\\s+")));

        return wordSet1.containsAll(wordSet2) || wordSet2.containsAll(wordSet1);
    }

}
