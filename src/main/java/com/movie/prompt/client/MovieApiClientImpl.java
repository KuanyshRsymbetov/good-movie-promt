package com.movie.prompt.client;

import com.movie.prompt.model.Movie;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class MovieApiClientImpl implements MovieApiClient {

    private final WebClient.Builder movieApiClient;

    @Value("${omdb.movie.apikey}")
    private String apikey;

    public MovieApiClientImpl(@Qualifier("movieApiClient") WebClient.Builder movieApiClient) {
        this.movieApiClient = movieApiClient;
    }

    @Override
    public Mono<Movie> getMovieData(String movieTitleName){
        return movieApiClient.build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("apikey", apikey)
                        .queryParam("t", movieTitleName)
                        .build())
                .header("Accept", APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(Movie.class);
    }
}
