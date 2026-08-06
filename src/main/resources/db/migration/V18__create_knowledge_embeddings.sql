CREATE TABLE knowledge_embeddings
(
    id BIGSERIAL PRIMARY KEY,

    document_source VARCHAR(50) NOT NULL,

    document_id BIGINT NOT NULL,

    title VARCHAR(255) NOT NULL,

    content TEXT NOT NULL,

    language VARCHAR(20) NOT NULL,

    embedding VECTOR(1536) NOT NULL,

    metadata TEXT,

    created_at TIMESTAMP NOT NULL
);

CREATE INDEX idx_document
ON knowledge_embeddings(
    document_source,
    document_id
);