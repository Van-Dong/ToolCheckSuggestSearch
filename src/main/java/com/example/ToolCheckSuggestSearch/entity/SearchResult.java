package com.example.ToolCheckSuggestSearch.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "search_result")
public class SearchResult {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String urlScreenshot;

    @CreationTimestamp
    LocalDate createdAt;

    Boolean isMatchFound = false;

    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "search_result_id")
    List<SuggestedKeyword> suggestedKeywords;

    @JsonIgnore
    @ManyToOne
    KeywordTracker keywordTracker;
}
