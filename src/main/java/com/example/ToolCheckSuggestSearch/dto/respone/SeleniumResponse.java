package com.example.ToolCheckSuggestSearch.dto.respone;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SeleniumResponse {
    String imageURl;
    List<String> suggestedKeywords;
}
