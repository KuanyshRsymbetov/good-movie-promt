package com.movie.prompt.service;

import com.movie.prompt.model.Movie;
import reactor.core.publisher.Mono;

public interface MovieApiService {

    Mono<Movie> serveMovie(String movieName);
}
