package com.peer39.bautruk.categorizer.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class CategoryKeyword {

    private final String phrase;

    public CategoryKeyword(String phrase) {
        if (phrase == null || phrase.trim().isEmpty()) {
            throw new IllegalArgumentException("Keyword phrase cannot be empty");
        }

        String[] words = phrase.trim().split("\\s+");
        if (words.length > 6) {
            throw new IllegalArgumentException("Keyword phrase cannot contain more than 6 words");
        }

        this.phrase = phrase.toLowerCase().trim();
    }
}