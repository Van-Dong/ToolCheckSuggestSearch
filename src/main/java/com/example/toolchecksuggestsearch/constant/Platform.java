package com.example.toolchecksuggestsearch.constant;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum Platform {
    GOOGLE("https://www.google.com", "textarea[name='q']", "#Alh6id ul.G43f7e li", "div > div.aajZCb > div > ul li"),
    YAHOO("https://www.search.yahoo.com", "input[name='p']", "ul.sa-list li", "ul.sa-list li"),
    BING("https://www.bing.com", "textarea[name='q']", "#sa_sug_block ul li", "#sa_sug_block ul li");

    String url;
    String inputSearchSelector;
    String ulResultSelector;
    String ulResultSelectorInMobile;

    Platform(String url, String inputSearchSelector, String ulResultSelector, String ulResultSelectorInMobile) {
        this.url = url;
        this.inputSearchSelector = inputSearchSelector;
        this.ulResultSelector = ulResultSelector;
        this.ulResultSelectorInMobile = ulResultSelectorInMobile;
    }
}
