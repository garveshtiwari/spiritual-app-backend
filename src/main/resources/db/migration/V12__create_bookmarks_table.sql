CREATE TABLE bookmarks
(
    id BIGSERIAL PRIMARY KEY,

    user_id BIGINT NOT NULL,

    verse_id BIGINT NOT NULL,

    note TEXT,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_bookmarks_user
        FOREIGN KEY (user_id)
            REFERENCES users(id)
            ON DELETE CASCADE,

    CONSTRAINT fk_bookmarks_verse
        FOREIGN KEY (verse_id)
            REFERENCES verses(id)
            ON DELETE CASCADE,

    CONSTRAINT uk_user_verse
        UNIQUE (user_id, verse_id)
);