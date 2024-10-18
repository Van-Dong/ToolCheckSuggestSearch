package com.example.toolchecksuggestsearch.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.toolchecksuggestsearch.entity.SearchResult;

@Repository
public interface SearchResultRepository extends JpaRepository<SearchResult, Long> {

    // Find Search Result of KeywordTracker in Month with Sort
    @Query("SELECT sr FROM SearchResult sr " + "WHERE sr.keywordTracker.id = :keywordTrackerId "
            + "AND FUNCTION('MONTH', sr.createdAt) = :month "
            + "AND FUNCTION('YEAR', sr.createdAt) = :year "
            + "ORDER BY sr.createdAt ASC")
    List<SearchResult> findAllWithCreatedAtInMonthAndByKeywordTrackerId(
            @Param("month") int month, @Param("year") int year, @Param("keywordTrackerId") Long keywordTrackerId);

    //  Search oldest SearchResult record to get oldest month
    Optional<SearchResult> findFirstByOrderByCreatedAtAsc();
}
