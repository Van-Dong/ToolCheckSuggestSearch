package com.example.toolchecksuggestsearch.dto.respone;

import java.util.List;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SeleniumResponse {
    String imageURl;
    List<String> suggestedKeywords;
}
