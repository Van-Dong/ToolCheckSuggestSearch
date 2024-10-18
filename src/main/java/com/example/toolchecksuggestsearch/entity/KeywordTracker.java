package com.example.toolchecksuggestsearch.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import com.example.toolchecksuggestsearch.constant.Platform;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class KeywordTracker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, columnDefinition = "VARCHAR(100) COLLATE utf8mb4_unicode_ci")
    String searchKeyword;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    String matchKeywords;

    Byte frequencyCheck;

    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    Platform platform;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword_tracker_id")
    List<SearchResult> searchResults;

    // partial == False, all == True
    Boolean isAllMatch;
    Boolean isPC;

    @Column(length = 100)
    String remark;

    @CreationTimestamp
    LocalDate createdAt;
}
