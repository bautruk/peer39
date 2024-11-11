package com.peer39.bautruk.categorizer.controller;

import com.peer39.bautruk.categorizer.dto.CategorizationRequest;
import com.peer39.bautruk.categorizer.dto.CategorizationResult;
import com.peer39.bautruk.categorizer.service.CategorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categorize")
@RequiredArgsConstructor
public class CategorizationController {

    private final CategorizationService categorizationService;


    @PostMapping
    public List<CategorizationResult> categorizeUrls(@Valid @RequestBody CategorizationRequest request) {
        return categorizationService.categorizeUrls(request);
    }
}

