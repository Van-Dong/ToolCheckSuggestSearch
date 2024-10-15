package com.example.ToolCheckSuggestSearch.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class SearchResult {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String urlScreenshot;
    String searchResults;

    @ManyToOne
    Keyword keyword;
}
