package com.garveshtiwari.spiritual_app_backend
        .intelligence.search.repository;

import com.garveshtiwari.spiritual_app_backend
        .intelligence.search.dto.SearchResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Array;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SearchRepositoryImpl
        implements SearchRepository {

    private final JdbcTemplate
            jdbcTemplate;


    @Override
    public List<SearchResult> search(
            String vector,
            List<Long> preferredBookIds,
            Integer limit
    ) {

        long start =
                System.currentTimeMillis();


        List<SearchResult> results;

        if (preferredBookIds == null ||
                preferredBookIds.isEmpty()) {

            results =
                    searchAllBooks(
                            vector,
                            limit
                    );

        } else {

            results =
                    searchPreferredBooks(
                            vector,
                            preferredBookIds,
                            limit
                    );
        }


        long duration =
                System.currentTimeMillis()
                        - start;

        log.info(
                "SEARCH REPOSITORY TIME: {} ms",
                duration
        );

        return results;
    }


    /*
     * =========================================================
     * SEARCH PREFERRED BOOKS
     * =========================================================
     */

    private List<SearchResult> searchPreferredBooks(
            String vector,
            List<Long> preferredBookIds,
            Integer limit
    ) {

        String sql = """
                SELECT
                    document_id,
                    title,
                    content,
                    metadata,
                    book_id,
                    embedding <=> CAST(? AS vector)
                        AS similarity
                FROM knowledge_embeddings
                WHERE book_id = ANY (?)
                ORDER BY similarity
                LIMIT ?
                """;

        return jdbcTemplate.query(
                connection -> {

                    var statement =
                            connection.prepareStatement(
                                    sql
                            );

                    statement.setString(
                            1,
                            vector
                    );

                    Array bookIds =
                            connection.createArrayOf(
                                    "bigint",
                                    preferredBookIds.toArray()
                            );

                    statement.setArray(
                            2,
                            bookIds
                    );

                    statement.setInt(
                            3,
                            limit
                    );

                    return statement;
                },

                (rs, rowNum) ->
                        SearchResult
                                .builder()
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
                                .rankingScore(
                                        rs.getDouble(
                                                "similarity"
                                        )
                                )
                                .build()
        );
    }


    /*
     * =========================================================
     * SEARCH ALL BOOKS
     * =========================================================
     */

    private List<SearchResult> searchAllBooks(
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
                        SearchResult
                                .builder()
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
                                .rankingScore(
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