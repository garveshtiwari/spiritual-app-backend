CREATE TABLE long_term_memories (

    id BIGSERIAL PRIMARY KEY,

    user_id BIGINT NOT NULL,

    content TEXT NOT NULL,

    embedding TEXT,

    category VARCHAR(50) NOT NULL,

    importance VARCHAR(50) NOT NULL,

    status VARCHAR(50) NOT NULL,

    source VARCHAR(50) NOT NULL,

    confidence INTEGER NOT NULL,

    pinned BOOLEAN NOT NULL,

    progress INTEGER NOT NULL,

    valid_until TIMESTAMP,

    created_at TIMESTAMP,

    updated_at TIMESTAMP,

    last_accessed_at TIMESTAMP,

    access_count INTEGER NOT NULL,

    CONSTRAINT fk_long_term_memory_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);