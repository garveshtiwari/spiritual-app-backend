CREATE TABLE password_reset_otps
(
    id BIGSERIAL PRIMARY KEY,

    otp VARCHAR(6) NOT NULL,

    user_id BIGINT NOT NULL UNIQUE,

    expiry_time TIMESTAMP NOT NULL,

    verified BOOLEAN NOT NULL,

    CONSTRAINT fk_password_reset_otp_user
        FOREIGN KEY (user_id)
            REFERENCES users(id)
            ON DELETE CASCADE
);