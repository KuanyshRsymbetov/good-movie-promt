package com.movie.prompt.service;

import reactor.core.publisher.Mono;

public interface GptService {

    Mono<String> getChatResponse(String prompt);
}
