CREATE TABLE reading_history
(
    id BIGSERIAL PRIMARY KEY,

    user_id BIGINT NOT NULL,

    verse_id BIGINT NOT NULL,

    opened_at TIMESTAMP NOT NULL,

    duration_in_seconds BIGINT,

    CONSTRAINT fk_history_user
        FOREIGN KEY (user_id)
            REFERENCES users(id)
            ON DELETE CASCADE,

    CONSTRAINT fk_history_verse
        FOREIGN KEY (verse_id)
            REFERENCES verses(id)
            ON DELETE CASCADE
);