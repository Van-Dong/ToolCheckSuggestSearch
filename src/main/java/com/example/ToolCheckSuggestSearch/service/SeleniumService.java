package com.example.ToolCheckSuggestSearch.service;

import com.example.ToolCheckSuggestSearch.constant.Platform;
import com.example.ToolCheckSuggestSearch.dto.respone.SeleniumResponse;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class SeleniumService {
    public SeleniumResponse retrieveKeywordSuggestionsWithScreenshot(String searchKeyword, Platform platform, Boolean isPC) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        try {
            // Open website
            driver.get(platform.getUrl());

            // Fill keywords in the form and wait result
            searchAndWaitForSuggestions(searchKeyword, driver);

            // Store file
            String nameScreenshot = storeScreenshot(driver);

            // Take all suggested keyword
            List<WebElement> suggestions = driver.findElements(By.cssSelector("div.mkHrUc ul.G43f7e li")); // CSS selector cho các gợi ý
            List<String> keywordSuggestions = suggestions.stream().map(webElement -> webElement.getText().toLowerCase()).toList();

            // Test
            log.info("REsult test: {}", keywordSuggestions);

            return SeleniumResponse.builder()
                    .suggestedKeywords(keywordSuggestions)
                    .imageURl(nameScreenshot)
                    .build();
        } catch (IOException e) {
            log.error("An error occurred while saving the file");
        } catch (TimeoutException e) {
            log.error("Timeout occurred while waiting for suggestions. Keyword: {}, URL: {}, Time: {}", searchKeyword, platform.name(), Instant.now());
            fallBack();
        } finally {
            driver.quit();
        }
        return null;
    }

    private void searchAndWaitForSuggestions(String searchKeyword, WebDriver driver) throws TimeoutException {
        // Fill keywords in the form
        WebElement searchInput = driver.findElement(By.cssSelector("textarea[name='q']"));
        searchInput.sendKeys(searchKeyword);

        // Wait for suggested keyword to show with timeout 10s
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("ul.G43f7e li"), 0));
    }

    private String storeScreenshot(WebDriver driver) throws IOException {
        // create directory screenshots/ if not exists
        Path screenshotStorageFolder = Paths.get("screenshots");
        if (!Files.exists(screenshotStorageFolder)) {
            Files.createDirectories(screenshotStorageFolder);
        }

        // Store screenshot file
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String nameScreenshot = Instant.now().getEpochSecond() + "_screenshot.png";
        Path destinationPath = screenshotStorageFolder.resolve(nameScreenshot);
        Files.copy(screenshot.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

        return nameScreenshot;
    }

    public void fallBack() {

    }
}
