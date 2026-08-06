CREATE TABLE chapters
(
    id BIGSERIAL PRIMARY KEY,

    book_id BIGINT NOT NULL,

    chapter_number INTEGER NOT NULL,

    title VARCHAR(255),

    summary TEXT,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_chapters_book
        FOREIGN KEY (book_id)
            REFERENCES books(id)
            ON DELETE CASCADE,

    CONSTRAINT uk_book_chapter
        UNIQUE (book_id, chapter_number)
);