package com.movie.prompt.service;

import com.movie.prompt.client.MovieApiClient;
import com.movie.prompt.model.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MovieApiServiceImpl implements MovieApiService{

    private final MovieApiClient movieApiClient;

    @Override
    public Mono<Movie> serveMovie(String movieName) {
        return movieApiClient.getMovieData(movieName);
    }
}
