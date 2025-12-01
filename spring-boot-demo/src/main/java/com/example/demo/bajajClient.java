package com.example.service;

import com.example.bajaj.dto.WebhookRequest;
import com.example.bajaj.dto.WebhookResponse;
import com.example.bajaj.dto.FinalQueryRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Service
public class bajajClient {

    private static final Logger log = LoggerFactory.getLogger(bajajClient.class);

    private final WebClient webClient;

    public bajajClient(
            @Value("${http.baseUrl}") String baseUrl,
            @Value("${http.connectTimeoutMs:10000}") int connectTimeoutMs,
            @Value("${http.readTimeoutMs:20000}") int readTimeoutMs
    ) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        log.debug("WebClient initialized: baseUrl={}", baseUrl);
    }

    /**
     * Calls Bajaj API to generate webhook + accessToken
     */
    public WebhookResponse generateWebhook(WebhookRequest body) {
        log.info("🔗 Generating webhook...");
        return webClient.post()
                .uri("/generateWebhook/JAVA")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(WebhookResponse.class)
                .doOnNext(resp -> log.info(" Webhook generated: {}", resp.getWebhook()))
                .block();
    }

    /**
     * Submits the final SQL query to the given webhook.
     */
    public String submitFinalQuery(String webhookUrl, String accessToken, String finalQuery) {
        log.info("📤 Submitting final query to webhook...");
        FinalQueryRequest request = new FinalQueryRequest(finalQuery);

        return WebClient.create()
                .post()
                .uri(webhookUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken) // raw token (no "Bearer")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(resp -> log.info(" Submission done, response={}", resp))
                .block();
    }

    /**
     * Persist content into a local file (for debugging / logs).
     */
    public static void persistResult(String content) {
        try {
            Path path = Path.of("submission_result.txt");
            Files.writeString(path, content,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE);
            log.info("Result persisted to {}", path.toAbsolutePath());
        } catch (Exception e) {
            log.error(" Failed to persist result", e);
        }
    }
}

