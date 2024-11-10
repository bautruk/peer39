package com.peer39.bautruk.categorizer.service;

import com.peer39.bautruk.categorizer.model.Category;
import com.peer39.bautruk.categorizer.model.CategoryKeyword;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class CategoryService {

    private final Map<String, Category> categories = new HashMap<>();

    @PostConstruct
    public void initialize() {
        // Initialize predefined categories
        Category starWars = new Category("Star Wars");
        Arrays.asList(
            "star war", "starwars", "starwar", "r2d2",
            "may the force be with you"
        ).forEach(keyword -> starWars.addKeyword(new CategoryKeyword(keyword)));
        addCategory(starWars);

        Category basketball = new Category("Basketball");
        Arrays.asList(
            "basketball", "nba", "ncaa", "lebron james",
            "john stockton", "anthony davis"
        ).forEach(keyword -> basketball.addKeyword(new CategoryKeyword(keyword)));
        addCategory(basketball);
    }

    public void addCategory(Category category) {
        categories.put(category.getName().toLowerCase(), category);
    }
}
