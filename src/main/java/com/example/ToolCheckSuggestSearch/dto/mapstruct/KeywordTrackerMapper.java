package com.example.ToolCheckSuggestSearch.dto.mapstruct;

import com.example.ToolCheckSuggestSearch.dto.request.CreateKeywordTrackerRequest;
import com.example.ToolCheckSuggestSearch.entity.KeywordTracker;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface KeywordTrackerMapper {
    KeywordTracker toKeywordTracker(CreateKeywordTrackerRequest request);
}
