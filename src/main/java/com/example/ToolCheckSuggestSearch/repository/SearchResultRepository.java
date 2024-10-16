package com.example.ToolCheckSuggestSearch.repository;

import com.example.ToolCheckSuggestSearch.entity.SearchResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchResultRepository extends JpaRepository<SearchResult, Long> {
    @Query("SELECT sr FROM SearchResult sr " +
            "WHERE sr.keywordTracker.id = :keywordTrackerId " +
            "AND FUNCTION('MONTH', sr.createdAt) = :month " +
            "AND FUNCTION('YEAR', sr.createdAt) = :year " +
            "ORDER BY sr.createdAt ASC")
    List<SearchResult> findAllWithCreatedAtInMonthAndByKeywordTrackerId(@Param("month") int month, @Param("year") int year,
                                                                        @Param("keywordTrackerId") Long keywordTrackerId);
}
