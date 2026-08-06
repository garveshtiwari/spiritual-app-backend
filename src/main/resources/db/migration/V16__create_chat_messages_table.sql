CREATE TABLE chat_messages
(
    id BIGSERIAL PRIMARY KEY,

    conversation_id BIGINT NOT NULL,

    sender_type VARCHAR(50) NOT NULL,

    message TEXT NOT NULL,

    created_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_chat_messages_conversation
        FOREIGN KEY (conversation_id)
            REFERENCES conversations(id)
            ON DELETE CASCADE
);