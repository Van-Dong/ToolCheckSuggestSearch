package com.example.ToolCheckSuggestSearch.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class SuggestedKeyword {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false,  columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    String keyword;
    Boolean isMatching;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    SearchResult searchResult;
}
