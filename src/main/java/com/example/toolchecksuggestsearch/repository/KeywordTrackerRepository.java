package com.example.toolchecksuggestsearch.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.toolchecksuggestsearch.entity.KeywordTracker;

@Repository
public interface KeywordTrackerRepository extends JpaRepository<KeywordTracker, Long> {

    // Find All KeywordTracker were searched (checked) in Month-Year
    @Query("SELECT kt FROM KeywordTracker kt JOIN FETCH kt.searchResults sr "
            + "WHERE FUNCTION('MONTH', sr.createdAt) = :month AND FUNCTION('YEAR', sr.createdAt) = :year")
    Page<KeywordTracker> findKeywordTrackersWithSearchResultsInMonth(
            @Param("month") int month, @Param("year") int year, Pageable pageable);
}
