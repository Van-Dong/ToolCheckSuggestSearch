package com.example.toolchecksuggestsearch.dto.request;

import jakarta.validation.constraints.*;

import com.example.toolchecksuggestsearch.constant.Platform;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateKeywordTrackerRequest {
    @NotBlank(message = "Search Keyword is required")
    String searchKeyword;

    @NotBlank(message = "Match Keyword is required")
    String matchKeywords;

    @NotNull(message = "Frequency is required")
    @Min(value = 1, message = "Frequency Check must be in [1, 30]")
    @Max(value = 30, message = "Frequency Check must be in [1, 30]")
    Byte frequencyCheck;

    @NotNull(message = "Platform is required")
    Platform platform;

    @NotNull(message = "Matching Pattern is required")
    Boolean isAllMatch;

    @NotNull(message = "Device is required")
    Boolean isPC;

    @Size(max = 100, message = "Remark must be maximum 100 characters")
    String remark;
}
