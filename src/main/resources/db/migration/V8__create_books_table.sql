CREATE TABLE books
(
    id BIGSERIAL PRIMARY KEY,

    name VARCHAR(255) NOT NULL,

    slug VARCHAR(255) NOT NULL UNIQUE,

    description TEXT,

    language VARCHAR(50) NOT NULL,

    author VARCHAR(255),

    category VARCHAR(100) NOT NULL,

    cover_image_url TEXT,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL
);