package com.example.ToolCheckSuggestSearch.dto.request;

import com.example.ToolCheckSuggestSearch.constant.Platform;
import com.example.ToolCheckSuggestSearch.entity.SearchResult;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateKeywordTrackerRequest {
    @NotBlank
    String searchKeyword;

    @NotBlank
    String matchKeywords;

    @NotNull
    @Min(value = 1)
    @Max(value = 30)
    Byte frequencyCheck;

    @NotNull
    Platform platform;

    @NotNull
    Boolean isAllMatch;

    @NotNull
    Boolean isPC;

    String remark;
}
