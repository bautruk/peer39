package com.peer39.bautruk.categorizer.repository;

import com.peer39.bautruk.categorizer.entity.Category;
import com.peer39.bautruk.categorizer.entity.CategoryKeyword;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Repository
public class CategoryRepository {

    private final Set<Category> categories = new HashSet<>();

    @PostConstruct
    public void initialize() {
        // Initialize predefined categories
        Category starWars = new Category("Star Wars");
        Arrays.asList(
            "star war", "starwars", "starwar", "r2d2", "star wars",
            "may the force be with you"
        ).forEach(keyword -> starWars.addKeyword(new CategoryKeyword(keyword)));
        categories.add(starWars);

        Category basketball = new Category("Basketball");
        Arrays.asList(
            "basketball", "nba", "ncaa", "lebron james",
            "john stockton", "anthony davis"
        ).forEach(keyword -> basketball.addKeyword(new CategoryKeyword(keyword)));
        categories.add(basketball);
    }

    public Set<Category> findAll() {
        return categories;
    }
}
