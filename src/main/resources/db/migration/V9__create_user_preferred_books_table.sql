CREATE TABLE user_preferred_books
(
    id BIGSERIAL PRIMARY KEY,

    user_preference_id BIGINT NOT NULL,

    book_id BIGINT NOT NULL,

    created_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_user_preferred_books_preferences
        FOREIGN KEY (user_preference_id)
            REFERENCES user_preferences(id)
            ON DELETE CASCADE,

    CONSTRAINT fk_user_preferred_books_books
        FOREIGN KEY (book_id)
            REFERENCES books(id)
            ON DELETE CASCADE,

    CONSTRAINT uk_user_preferred_book
        UNIQUE (user_preference_id, book_id)
);