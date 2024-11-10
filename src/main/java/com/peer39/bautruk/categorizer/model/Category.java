package com.peer39.bautruk.categorizer.model;

import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode
public class Category {
    private final String name;

    private final Set<CategoryKeyword> keywords;

    public Category(String name) {
        this.name = name;
        this.keywords = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void addKeyword(CategoryKeyword keyword) {
        keywords.add(keyword);
    }

    public Set<CategoryKeyword> getKeywords() {
        return Collections.unmodifiableSet(keywords);
    }

}
