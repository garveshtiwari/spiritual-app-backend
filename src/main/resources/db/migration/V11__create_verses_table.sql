CREATE TABLE verses
(
    id BIGSERIAL PRIMARY KEY,

    chapter_id BIGINT NOT NULL,

    verse_number INTEGER NOT NULL,

    original_text TEXT,

    transliteration TEXT,

    translation TEXT,

    explanation TEXT,

    audio_url TEXT,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_verses_chapter
        FOREIGN KEY (chapter_id)
            REFERENCES chapters(id)
            ON DELETE CASCADE,

    CONSTRAINT uk_chapter_verse
        UNIQUE (chapter_id, verse_number)
);