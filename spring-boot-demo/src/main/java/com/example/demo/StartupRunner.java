package com.example.bajaj;

import com.example.bajaj.dto.WebhookResponse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.file.Files;

@Component
public class StartupRunner implements CommandLineRunner {

    private final WebClient webClient = WebClient.create();

    @Override
    public void run(String... args) throws Exception {
        // 1. Generate webhook
        WebhookResponse response = webClient.post()
                .uri("https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA")
                .bodyValue(new Request("Ramsai Sapuri", "22BCE2249", "ramsapuri@gmail.com"))
                .retrieve()
                .bodyToMono(WebhookResponse.class)
                .block();

        if (response == null) {
            System.err.println("Failed to generate webhook!");
            return;
        }

        String webhook = response.getWebhook();
        String token = response.getAccessToken();

        System.out.println(" Webhook: " + webhook);
        System.out.println(" Token: " + token);

        // 2. Read final SQL from resources
        String sql = new String(
                Files.readAllBytes(new ClassPathResource("final.sql").getFile().toPath())
        );

        // 3. Submit solution
        String result = webClient.post()
                .uri(webhook)
                .header("Authorization", token) // API expects raw token (not Bearer)
                .bodyValue(new Submission(sql))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println(" Submission response: " + result);
    }

    //  DTO classes (instead of record)
    public static class Request {
        private String name;
        private String regNo;
        private String email;

        public Request(String name, String regNo, String email) {
            this.name = name;
            this.regNo = regNo;
            this.email = email;
        }

        public String getName() { return name; }
        public String getRegNo() { return regNo; }
        public String getEmail() { return email; }
    }

    public static class Submission {
        private String finalQuery;

        public Submission(String finalQuery) {
            this.finalQuery = finalQuery;
        }

        public String getFinalQuery() { return finalQuery; }
    }
}
