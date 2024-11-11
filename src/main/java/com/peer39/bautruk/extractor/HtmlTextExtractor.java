package com.peer39.bautruk.extractor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class HtmlTextExtractor {
    private static final Pattern SCRIPTS_PATTERN = Pattern.compile("<script[^>]*>.*?</script>", Pattern.DOTALL);
    private static final Pattern STYLE_PATTERN = Pattern.compile("<style[^>]*>.*?</style>", Pattern.DOTALL);
    private static final Pattern HTML_TAGS_PATTERN = Pattern.compile("<[^>]+>");
    private static final Pattern MULTIPLE_SPACES = Pattern.compile("\\s+");

    public String extractText(String html) {
        if (html == null || html.isEmpty()) {
            return "";
        }

        // Remove scripts
        String result = SCRIPTS_PATTERN.matcher(html).replaceAll("");

        // Remove styles
        result = STYLE_PATTERN.matcher(result).replaceAll("");

        // Remove HTML tags
        result = HTML_TAGS_PATTERN.matcher(result).replaceAll(" ");

        // Decode HTML entities
        result = decodeHtmlEntities(result);

        // Clean up whitespace
        result = MULTIPLE_SPACES.matcher(result).replaceAll(" ").trim().toLowerCase();

        return result;
    }

    private String decodeHtmlEntities(String html) {
        return html.replaceAll("&amp;", "&")
            .replaceAll("&lt;", "<")
            .replaceAll("&gt;", ">")
            .replaceAll("&quot;", "\"")
            .replaceAll("&apos;", "'")
            .replaceAll("&#x27;", "'")
            .replaceAll("&#x2F;", "/")
            .replaceAll("&#39;", "'")
            .replaceAll("&#47;", "/");
    }
}
