package com.example.toolchecksuggestsearch.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.toolchecksuggestsearch.constant.Platform;
import com.example.toolchecksuggestsearch.dto.respone.SeleniumResponse;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SeleniumService {

    @Value("${selenium.max-retries}")
    private int maxRetries; // Maximum number of attempts

    @Value("${selenium.wait-timeout}")
    private int waitTimeout;

    @Value("${selenium.mobile-emulation}")
    private String mobileEmulation;

    public SeleniumResponse retrieveKeywordSuggestionsWithScreenshot(
            String searchKeyword, Platform platform, boolean isPC) {
        int retryCount = 0;

        // Try again if failed
        while (retryCount < maxRetries) {
            WebDriver driver = getWebDriver(isPC);

            try {
                // Open website
                driver.get(platform.getUrl());

                // Fill keywords in the form and wait result
                searchAndWaitForSuggestions(searchKeyword, platform, isPC, driver);

                // Store screenshot file
                String nameScreenshot = storeScreenshot(driver);

                // Take all suggested keyword
                List<String> keywordSuggestions = getKeywordSuggestions(platform, isPC, driver);

                return SeleniumResponse.builder()
                        .suggestedKeywords(keywordSuggestions)
                        .imageURl(nameScreenshot)
                        .build();
            } catch (IOException e) {
                log.error("An error occurred while saving the file");
                break;
            } catch (TimeoutException e) {
                retryCount++;
                log.error("Timeout after {} tries with Keyword: {}", retryCount, searchKeyword);
            } finally {
                driver.quit();
            }
        }

        return null;
    }

    private WebDriver getWebDriver(boolean isPC) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        if (!isPC) {
            options.setExperimentalOption("mobileEmulation", Map.of("deviceName", mobileEmulation));
        }
        return new ChromeDriver(options);
    }

    private void searchAndWaitForSuggestions(String searchKeyword, Platform platform, boolean isPC, WebDriver driver)
            throws TimeoutException {
        // Fill keywords in the form
        WebElement searchInput = driver.findElement(By.cssSelector(platform.getInputSearchSelector()));
        searchInput.sendKeys(searchKeyword);

        // Wait for suggested keyword to show with timeout 10s
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTimeout));
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
                By.cssSelector(isPC ? platform.getUlResultSelector() : platform.getUlResultSelectorInMobile()), 0));
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

    private List<String> getKeywordSuggestions(Platform platform, boolean isPC, WebDriver driver) {
        String ulElementContainResultSelector =
                isPC ? platform.getUlResultSelector() : platform.getUlResultSelectorInMobile();
        List<WebElement> suggestions = driver.findElements(
                By.cssSelector(ulElementContainResultSelector)); // CSS selector cho các gợi ý
        return suggestions.stream()
                .map(webElement -> webElement.getText().toLowerCase())
                .toList();
    }
}
