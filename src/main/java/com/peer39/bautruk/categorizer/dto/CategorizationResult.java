package com.peer39.bautruk.categorizer.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class CategorizationResult {
    private String url;
    private Set<String> matchingCategories;
}
