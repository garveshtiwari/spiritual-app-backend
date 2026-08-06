ALTER TABLE password_reset_otps
    ALTER COLUMN otp TYPE VARCHAR(255);

ALTER TABLE password_reset_otps
    ADD COLUMN created_at TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE password_reset_otps
    ADD COLUMN attempt_count INTEGER NOT NULL
        DEFAULT 0;