package com.example.ToolCheckSuggestSearch.constant;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum Platform {
    GOOGLE("https://www.google.com"),
    YAHOO("https://www.yahoo.com"),
    BING("https://www.bing.com");

    String url;
    Platform(String url) {
        this.url = url;
    }
}
