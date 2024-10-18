package com.example.toolchecksuggestsearch.testmethod;

import java.time.Duration;
import java.util.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.example.toolchecksuggestsearch.constant.Platform;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Test {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("mobileEmulation", Map.of("deviceName", "iPhone XR"));
        WebDriver driver = new ChromeDriver(options);
        try {
            Platform platform = Platform.GOOGLE;
            driver.get(platform.getUrl());
            // Fill keywords in the form
            WebElement searchInput = driver.findElement(By.cssSelector(platform.getInputSearchSelector()));
            searchInput.sendKeys("Hello Vietnam");

            // Wait for suggested keyword to show with timeout 10s
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
                    By.cssSelector(platform.getUlResultSelectorInMobile()), 0));

            // Take all suggested keyword
            List<WebElement> suggestions = driver.findElements(
                    By.cssSelector(platform.getUlResultSelectorInMobile())); // CSS selector cho các gợi ý
            List<String> keywordSuggestions = suggestions.stream()
                    .map(webElement -> webElement.getText().toLowerCase())
                    .toList();

            // Test
            System.out.println("Result test: " + keywordSuggestions);
        } finally {
            driver.quit();
        }
    }
}
