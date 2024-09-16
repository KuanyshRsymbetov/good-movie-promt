package com.movie.prompt.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

    @Value("${omdb.movie.baseusrl}")
    private String movieApiBaseUrl;

    @Value("${openai.api-key}")
    private String apiKey;

    @Value("${openai.base-url}")
    private String chatGptBaseUrl;

    @Bean(name = "movieApiClient")
    public WebClient.Builder movieApiClient(){
        return WebClient.builder()
                .baseUrl(movieApiBaseUrl)
                .defaultHeader(ACCEPT, APPLICATION_JSON_VALUE);
    }

    @Bean(name = "chatGptClient")
    public WebClient.Builder chatGptClient(){
        var httpClient = HttpClient.newConnection()
                .responseTimeout(Duration.ofSeconds(60))
                .keepAlive(false);
        return WebClient.builder()
                .baseUrl(chatGptBaseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .clientConnector(new ReactorClientHttpConnector(httpClient));
    }
}
