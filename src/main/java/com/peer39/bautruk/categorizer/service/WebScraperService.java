package com.peer39.bautruk.categorizer.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class WebScraperService {
//    @Async
//    public CompletableFuture<WebPageContent> scrapeUrl(String url) {
//        try {
//            String content = Jsoup.connect(url)
//                .timeout(10000)
//                .get()
//                .body()
//                .text();
//            return CompletableFuture.completedFuture(new WebPageContent(url, content));
//        } catch (Exception e) {
//            return CompletableFuture.completedFuture(
//                new WebPageContent(url, "Error: " + e.getMessage())
//            );
//        }
//    }
}

