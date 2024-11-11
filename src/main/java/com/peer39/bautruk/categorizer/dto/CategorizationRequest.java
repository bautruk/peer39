package com.peer39.bautruk.categorizer.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class CategorizationRequest {
    @NotEmpty
    private List<String> urls;
}
