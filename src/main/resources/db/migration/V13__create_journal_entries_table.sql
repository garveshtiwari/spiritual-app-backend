CREATE TABLE journal_entries
(
    id BIGSERIAL PRIMARY KEY,

    user_id BIGINT NOT NULL,

    verse_id BIGINT,

    title VARCHAR(255),

    content TEXT NOT NULL,

    mood VARCHAR(50),

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_journal_user
        FOREIGN KEY (user_id)
            REFERENCES users(id)
            ON DELETE CASCADE,

    CONSTRAINT fk_journal_verse
        FOREIGN KEY (verse_id)
            REFERENCES verses(id)
            ON DELETE SET NULL
);