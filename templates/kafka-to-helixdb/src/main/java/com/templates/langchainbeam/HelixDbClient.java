package com.templates.langchainbeam;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class HelixDbClient {

    private final String endpoint;
    private final String baseUrl;
    private final HttpClient client;

    private static final String DEFAULT_BASE_URL = "http://localhost:6969";

    private HelixDbClient(String endpoint, String baseUrl) {
        this.endpoint = endpoint;
        this.baseUrl = baseUrl != null ? baseUrl : DEFAULT_BASE_URL;
        this.client = HttpClient.newHttpClient();
    }

    public static HelixDbClient initialize(String endpoint, String baseUrl) {
        return new HelixDbClient(endpoint, baseUrl);
    }

    public void writeData(float[] vectors, String content) throws Exception {

        String url = String.format("%s/%s", baseUrl, endpoint);
        String body = createRequest(vectors, content);

        System.err.println("url: " + url);

        System.out.println("req body: " + body);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .version(HttpClient.Version.HTTP_1_1)
                .header("Content-Type", "application/json")
                .header("Accept", "*/*")
                .POST(BodyPublishers.ofString(body, StandardCharsets.UTF_8))
                .build();

        try {
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

            System.out.println("Status code: " + response.statusCode());
            System.out.println("Inserted vectors for content: " + content);

        } catch (IOException | InterruptedException e) {
            throw e;
        }

    }

    private String createRequest(float[] vectors, String content) {

        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode rootNode = objectMapper.createObjectNode();

        ArrayNode embeddingsNode = objectMapper.createArrayNode();
        for (float v : vectors) {
            embeddingsNode.add(v);
        }

        rootNode.set("vector", embeddingsNode);
        rootNode.put("content", content);

        try {
            return objectMapper.writeValueAsString(rootNode);
        } catch (Exception e) {
            return null;
        }
    }

}