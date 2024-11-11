package com.peer39.bautruk.extractor;

import com.peer39.bautruk.extractor.exception.TextExtractorException;
import com.peer39.bautruk.extractor.model.UrlContent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Slf4j
@Service
@RequiredArgsConstructor
public class UrlTextExtractorService {

    private final HtmlTextExtractor htmlTextExtractor;
    private final Executor taskExecutor;
    private final RestTemplate restTemplate;

    public List<UrlContent> extractTextFromUrls(List<String> urls) {
        return urls.parallelStream()
            .map(this::extractTextFromUrl)
            .map(CompletableFuture::join)
            .toList();
    }

    public CompletableFuture<UrlContent> extractTextFromUrl(String urlString) {
        try {
            String html = restTemplate.getForObject(urlString, String.class);
            String text = htmlTextExtractor.extractText(html);
            return CompletableFuture.completedFuture(new UrlContent(urlString, text));
        } catch (Exception e) {
            throw new TextExtractorException("Error fetching URL: " + urlString, e);
        }
    }
}
