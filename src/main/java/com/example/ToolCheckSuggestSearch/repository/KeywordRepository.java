package com.example.ToolCheckSuggestSearch.repository;

import com.example.ToolCheckSuggestSearch.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {
}
