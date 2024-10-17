package com.example.ToolCheckSuggestSearch.dto.request;

import com.example.ToolCheckSuggestSearch.constant.Platform;
import com.example.ToolCheckSuggestSearch.entity.SearchResult;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

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
