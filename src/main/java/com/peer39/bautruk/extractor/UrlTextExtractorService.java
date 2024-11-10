package com.peer39.bautruk.extractor;

import com.peer39.bautruk.extractor.model.UrlContent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UrlTextExtractorService {

    private final HtmlTextExtractor htmlTextExtractor;
    private final Executor taskExecutor;
    private final RestTemplate restTemplate;

    public List<UrlContent> extractTextFromUrls(List<String> urls) {
        return urls.parallelStream()
            .map(url -> CompletableFuture.supplyAsync(() -> extractTextFromUrl(url), taskExecutor))
            .map(CompletableFuture::join)
            .collect(Collectors.toList());
    }

    private UrlContent extractTextFromUrl(String urlString) {
        try {
            String html = restTemplate.getForObject(urlString, String.class);
            String text = htmlTextExtractor.extractText(html);
            return new UrlContent(urlString, text);
        } catch (Exception e) {
            log.error("Error fetching URL: {}", urlString, e);
            return new UrlContent(urlString, "Error: " + e.getMessage());
        }
    }
}
