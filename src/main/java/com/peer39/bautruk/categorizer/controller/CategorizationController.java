package com.peer39.bautruk.categorizer.controller;

import com.peer39.bautruk.categorizer.dto.CategorizationRequest;
import com.peer39.bautruk.categorizer.dto.CategorizationResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categorize")
public class CategorizationController {

//    private final CategorizationService categorizationService;
//
//    public CategorizationController(CategorizationService categorizationService) {
//        this.categorizationService = categorizationService;
//    }
//
//    @PostMapping
//    public ResponseEntity<List<CategorizationResult>> categorizeUrls(
//        @Valid @RequestBody CategorizationRequest request) {
//        return ResponseEntity.ok(categorizationService.categorizeUrls(request));
//    }
}

