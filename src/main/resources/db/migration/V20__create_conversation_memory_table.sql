CREATE TABLE conversation_memory
(
    id BIGSERIAL PRIMARY KEY,

    conversation_id BIGINT NOT NULL UNIQUE,

    summary TEXT NOT NULL,

    last_summarized_message_id BIGINT NOT NULL,

    summary_version INTEGER NOT NULL,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_conversation_memory_conversation
        FOREIGN KEY (conversation_id)
        REFERENCES conversations(id),

    CONSTRAINT fk_conversation_memory_last_message
        FOREIGN KEY (last_summarized_message_id)
        REFERENCES chat_messages(id)
);