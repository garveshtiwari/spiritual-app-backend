package com.garveshtiwari.spiritual_app_backend.book.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "verses",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_chapter_verse",
                        columnNames = {
                                "chapter_id",
                                "verse_number"
                        }
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Verse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "chapter_id",
            nullable = false
    )
    private Chapter chapter;

    @Column(
            name = "verse_number",
            nullable = false
    )
    private Integer verseNumber;

    @Column(name = "original_text")
    private String originalText;

    @Column(columnDefinition = "TEXT")
    private String transliteration;

    @Column(columnDefinition = "TEXT")
    private String translation;

    @Column(columnDefinition = "TEXT")
    private String explanation;

    @Column(name = "audio_url")
    private String audioUrl;

    @Column(
            name = "created_at",
            nullable = false
    )
    private LocalDateTime createdAt;

    @Column(
            name = "updated_at",
            nullable = false
    )
    private LocalDateTime updatedAt;
}