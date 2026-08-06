CREATE TABLE user_preferences
(
    id BIGSERIAL PRIMARY KEY,

    user_id BIGINT NOT NULL UNIQUE,

    language VARCHAR(50) NOT NULL,

    theme VARCHAR(50) NOT NULL,

    notifications_enabled BOOLEAN NOT NULL,

    reminder_time TIME,

    sampradaya VARCHAR(50),

    voice_type VARCHAR(50),

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_user_preferences_user
        FOREIGN KEY (user_id)
            REFERENCES users(id)
            ON DELETE CASCADE
);