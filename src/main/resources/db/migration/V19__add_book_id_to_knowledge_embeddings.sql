ALTER TABLE knowledge_embeddings
ADD COLUMN book_id BIGINT;

UPDATE knowledge_embeddings ke
SET book_id = c.book_id
FROM verses v
JOIN chapters c
    ON c.id = v.chapter_id
WHERE ke.document_source = 'VERSE'
  AND ke.document_id = v.id;

ALTER TABLE knowledge_embeddings
ALTER COLUMN book_id SET NOT NULL;

CREATE INDEX idx_book
ON knowledge_embeddings(book_id);