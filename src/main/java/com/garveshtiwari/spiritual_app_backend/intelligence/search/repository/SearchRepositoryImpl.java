package com.garveshtiwari.spiritual_app_backend
        .intelligence.search.repository;

import com.garveshtiwari.spiritual_app_backend
        .intelligence.search.dto.SearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SearchRepositoryImpl
        implements SearchRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<SearchResult> search(
            String vector,
            Integer limit
    ) {

        String sql = """
                SELECT
                    document_id,
                    title,
                    content,
                    metadata,
                    embedding <=> CAST(? AS vector)
                        AS similarity
                FROM knowledge_embeddings
                ORDER BY similarity
                LIMIT ?
                """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) ->
                        SearchResult.builder()
                                .documentId(
                                        rs.getLong(
                                                "document_id"
                                        )
                                )
                                .title(
                                        rs.getString(
                                                "title"
                                        )
                                )
                                .content(
                                        rs.getString(
                                                "content"
                                        )
                                )
                                .metadata(
                                        rs.getString(
                                                "metadata"
                                        )
                                )
                                .similarity(
                                        rs.getDouble(
                                                "similarity"
                                        )
                                )
                                .build(),
                vector,
                limit
        );
    }
}