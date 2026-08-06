CREATE TABLE preferred_books
(
    id BIGSERIAL PRIMARY KEY,

    user_preference_id BIGINT NOT NULL,

    book_name VARCHAR(100) NOT NULL,

    CONSTRAINT fk_preferred_books_user_preferences
        FOREIGN KEY (user_preference_id)
            REFERENCES user_preferences(id)
            ON DELETE CASCADE
);