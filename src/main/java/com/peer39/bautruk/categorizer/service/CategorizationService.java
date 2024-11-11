package com.peer39.bautruk.categorizer.service;

import com.peer39.bautruk.categorizer.dto.CategorizationRequest;
import com.peer39.bautruk.categorizer.dto.CategorizationResult;
import com.peer39.bautruk.categorizer.entity.Category;
import com.peer39.bautruk.categorizer.entity.CategoryKeyword;
import com.peer39.bautruk.categorizer.repository.CategoryRepository;
import com.peer39.bautruk.extractor.UrlTextExtractorService;
import com.peer39.bautruk.extractor.model.UrlContent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategorizationService {

    private final UrlTextExtractorService urlTextExtractorService;
    private final CategoryRepository categoryRepository;

    public List<CategorizationResult> categorizeUrls(CategorizationRequest request) {
        List<CompletableFuture<UrlContent>> futures = request.getUrls()
            .stream()
            .map(urlTextExtractorService::extractTextFromUrl)
            .toList();

        return futures.stream()
            .map(CompletableFuture::join)
            .map(this::categorizeContent)
            .collect(Collectors.toList());
    }

    private CategorizationResult categorizeContent(UrlContent content) {
        Set<Category> categories = categoryRepository.findAll();
        Set<Integer> numberOfWordsInPhrase = categories.stream()
            .map(Category::getKeywords)
            .flatMap(Set::stream)
            .map(CategoryKeyword::getNumberOfWords)
            .collect(Collectors.toSet());
        Map<Integer, Set<String>> indexesByNumberOfWords = buildIndexes(content.content(), numberOfWordsInPhrase);
        Set<Category> matches = findMatching(categories, indexesByNumberOfWords);
        Set<String> collect = matches.stream()
            .map(Category::getName)
            .collect(Collectors.toSet());
        return CategorizationResult.builder()
            .url(content.url())
            .matchingCategories(collect)
            .build();
    }

    private Set<Category> findMatching(Set<Category> categories, Map<Integer, Set<String>> indexesByNumberOfWords) {
        Set<Category> result = new HashSet<>();
        for (Category category : categories) {
            Set<CategoryKeyword> keywords = category.getKeywords();
            for (CategoryKeyword keyword : keywords) {
                Set<String> phrases = indexesByNumberOfWords.get(keyword.getNumberOfWords());
                if (phrases != null && phrases.contains(keyword.getPhrase())) {
                    result.add(category);
                    break;
                }
            }
        }
        return result;
    }

    private Map<Integer, Set<String>> buildIndexes(String content, Set<Integer> numberOfWordsInPhrase) {
        return numberOfWordsInPhrase.stream()
            .collect(Collectors.toMap(
                numberOfWords -> numberOfWords,
                numberOfWords -> buildIndex(content, numberOfWords)
            ));
    }

    private Set<String> buildIndex(String content, Integer numberOfWords) {
        Set<String> phrases = new HashSet<>();
        String[] words = content.split("\\s+");

        for (int i = 0; i <= words.length - numberOfWords; i++) {
            StringBuilder phrase = new StringBuilder();
            for (int j = 0; j < numberOfWords; j++) {
                if (j > 0) {
                    phrase.append(" ");
                }
                phrase.append(words[i + j]);
            }
            phrases.add(phrase.toString());
        }
        return phrases;
    }
}
