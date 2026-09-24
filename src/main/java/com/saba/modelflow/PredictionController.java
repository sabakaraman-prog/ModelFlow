package com.saba.modelflow;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.MediaType;
import java.util.Map;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController // handles HTTP requests and responses
public class PredictionController {

    @GetMapping("/health")
    public String health() {
        return "ModelFlow is running";
    }

    @PostMapping(
        value = "/predict",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public String predict(@RequestBody Map<String, String> input) throws Exception {

        String text = input.get("text");

        // Prevent empty input
        if (text == null || text.trim().isEmpty()) {
            return "{\"error\":\"Text cannot be empty\"}";
        }

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(System.getenv().getOrDefault("PYTHON_SERVICE_URL", "http://localhost:8000/predict")))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(
                "{\"text\":\"" + text + "\"}"
            ))
            .build();

        long start = System.nanoTime();

        HttpResponse<String> response = client.send(
            request,
            HttpResponse.BodyHandlers.ofString()
        );

        double latencyMs =
            (System.nanoTime() - start) / 1_000_000.0;

        System.out.println(
            "Java request latency: " + latencyMs + " ms"
        );

        return response.body();
    }
}