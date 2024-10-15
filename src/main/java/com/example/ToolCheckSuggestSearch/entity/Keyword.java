package com.example.ToolCheckSuggestSearch.entity;

import com.example.ToolCheckSuggestSearch.constant.Platform;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Keyword {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String privateKeyword;
    String matchKeywords;
    Byte frequencyCheck;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    Platform platform;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn
    List<SearchResult> searchResults;

    // partial == False, all == True
    Boolean isAllMatch;

    String rewards;
}
