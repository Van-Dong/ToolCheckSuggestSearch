package com.example.toolchecksuggestsearch.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(
        name = "search_result",
        indexes = {@Index(name = "idx_search_result_created_at", columnList = "created_at")})
public class SearchResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String urlScreenshot;

    @CreationTimestamp
    LocalDate createdAt;

    Boolean isMatchFound = false;

    @OneToMany(
            cascade = {CascadeType.REMOVE, CascadeType.PERSIST},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "search_result_id")
    List<SuggestedKeyword> suggestedKeywords;

    @JsonIgnore
    @ManyToOne
    KeywordTracker keywordTracker;
}
