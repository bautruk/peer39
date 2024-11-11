package com.peer39.bautruk.categorizer;

import com.peer39.bautruk.categorizer.dto.CategorizationRequest;
import com.peer39.bautruk.categorizer.dto.CategorizationResult;
import com.peer39.bautruk.categorizer.service.CategorizationService;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest
public class CategorizationServiceTest {

    @Autowired
    private CategorizationService categorizationService;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @BeforeEach
    void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void testCategorization() throws IOException {
        // arrange
        mockServer.expect(requestTo("https://www.starwars.com/news/everything-we-know-about-the-mandalorian"))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withSuccess(
                new String(Files.readAllBytes(new ClassPathResource("/pages/sw.html").getFile().toPath())),
                org.springframework.http.MediaType.TEXT_HTML));

        CategorizationRequest request = new CategorizationRequest();
        request.setUrls(List.of("https://www.starwars.com/news/everything-we-know-about-the-mandalorian"));

        // act
        var results = categorizationService.categorizeUrls(request);

        // assert
        assertNotNull(results);
        assertEquals(1, results.size());

        CategorizationResult result = results.get(0);
        assertEquals("https://www.starwars.com/news/everything-we-know-about-the-mandalorian", result.getUrl());
        assertEquals(1, result.getMatchingCategories().size());
        assertTrue(result.getMatchingCategories().contains("Star Wars"));
    }
}
