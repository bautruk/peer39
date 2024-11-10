package com.peer39.bautruk.extractor;

import com.peer39.bautruk.extractor.model.UrlContent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest
class UrlTextExtractorServiceTest {

    @Autowired
    UrlTextExtractorService service;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @BeforeEach
    void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void extractTextFromBbd() throws IOException {
        // arrange
        mockServer.expect(requestTo("https://www.bbc.com"))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withSuccess(
                new String(Files.readAllBytes(new ClassPathResource("/pages/bbc.html").getFile().toPath())),
                org.springframework.http.MediaType.TEXT_HTML));

        List<String> urls = Arrays.asList(
            "https://www.bbc.com"
        );

        // act
        List<UrlContent> results = service.extractTextFromUrls(urls);

        // assert
        //todo really poor assertion
        assertNotNull(results);
        assertEquals(1, results.size());
        results.forEach(result -> {
            assertNotNull(result.url());
            assertNotNull(result.content());
        });

        mockServer.verify();
    }

    @Test
    void extractTextFromCnn() throws IOException {
        // arrange
        mockServer.expect(requestTo("https://edition.cnn.com/"))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withSuccess(
                new String(Files.readAllBytes(new ClassPathResource("pages/cnn.html").getFile().toPath())),
                org.springframework.http.MediaType.TEXT_HTML));

        List<String> urls = Arrays.asList(
            "https://edition.cnn.com/"
        );

        // act
        List<UrlContent> results = service.extractTextFromUrls(urls);

        // assert
        //todo really poor assertion
        assertNotNull(results);
        assertEquals(1, results.size());
        results.forEach(result -> {
            assertNotNull(result.url());
            assertNotNull(result.content());
        });

        mockServer.verify();
    }
}