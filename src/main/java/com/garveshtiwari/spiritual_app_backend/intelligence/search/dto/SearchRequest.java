package com.garveshtiwari.spiritual_app_backend.intelligence.search.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {

    private String query;

    private Integer limit;

    /**
     * Preferred books of the user.
     * Empty or null means search all books.
     */
    private List<Long> preferredBookIds;
}