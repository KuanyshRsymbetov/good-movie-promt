package com.movie.prompt.service;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Component
public class GptServiceImpl implements GptService {

    @Value("${openai.api-key}")
    private String apiKey;

    private final WebClient.Builder chatGptClient;

    public GptServiceImpl(@Qualifier("chatGptClient") WebClient.Builder chatGptClient) {
        this.chatGptClient = chatGptClient;
    }

    @Override
    public Mono<String> getChatResponse(String prompt) {

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", new Object[]{
                Map.of("role", "system", "content", "You are a helpful assistant."),
                Map.of("role", "user", "content", prompt)
        });

        Gson gson = new Gson();
        String jsonBody = gson.toJson(requestBody);

        return chatGptClient.build()
                .post()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .bodyValue(buildRequestBody(prompt))
                .retrieve()
                .bodyToMono(String.class);
    }


    private String buildRequestBody(String prompt) {
        return """
               {
                 "model": "gpt-3.5-turbo",
                 "messages": [
                   {"role": "system", "content": "You are a helpful assistant."},
                   {"role": "user", "content": "%s"}
                 ]
               }
               """.formatted(prompt);
    }
}
