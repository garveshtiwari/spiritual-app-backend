package com.garveshtiwari.spiritual_app_backend.book.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "chapters",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_book_chapter",
                        columnNames = {
                                "book_id",
                                "chapter_number"
                        }
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "book_id",
            nullable = false
    )
    private Book book;

    @Column(
            name = "chapter_number",
            nullable = false
    )
    private Integer chapterNumber;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String summary;

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