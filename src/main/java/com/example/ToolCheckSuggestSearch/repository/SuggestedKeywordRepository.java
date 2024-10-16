package com.example.ToolCheckSuggestSearch.repository;

import com.example.ToolCheckSuggestSearch.entity.SuggestedKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuggestedKeywordRepository extends JpaRepository<SuggestedKeyword, Long> {
}
