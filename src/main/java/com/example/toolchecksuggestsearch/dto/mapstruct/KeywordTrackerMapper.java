package com.example.toolchecksuggestsearch.dto.mapstruct;

import org.mapstruct.Mapper;

import com.example.toolchecksuggestsearch.dto.request.CreateKeywordTrackerRequest;
import com.example.toolchecksuggestsearch.entity.KeywordTracker;

@Mapper(componentModel = "spring")
public interface KeywordTrackerMapper {
    KeywordTracker toKeywordTracker(CreateKeywordTrackerRequest request);
}
