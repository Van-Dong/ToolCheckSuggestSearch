package com.example.ToolCheckSuggestSearch.repository;

import com.example.ToolCheckSuggestSearch.entity.KeywordTracker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface KeywordTrackerRepository extends JpaRepository<KeywordTracker, Long> {
    @Query("SELECT kt FROM KeywordTracker kt JOIN FETCH kt.searchResults sr " +
            "WHERE FUNCTION('MONTH', sr.createdAt) = :month AND FUNCTION('YEAR', sr.createdAt) = :year")
    Page<KeywordTracker> findKeywordTrackersWithSearchResultsInMonth(@Param("month") int month, @Param("year") int year, Pageable pageable);
}
