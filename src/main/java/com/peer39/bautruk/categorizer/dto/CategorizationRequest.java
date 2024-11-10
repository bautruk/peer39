package com.peer39.bautruk.categorizer.dto;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class CategorizationRequest {
    @NotEmpty
    private List<String> urls;

    @NotEmpty
    private List<String> categories;

    // Getters and setters
}
