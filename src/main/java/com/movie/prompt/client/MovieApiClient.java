package com.movie.prompt.client;

import com.movie.prompt.model.Movie;
import reactor.core.publisher.Mono;

public interface MovieApiClient {

    Mono<Movie> getMovieData(String movieTitleName);
}
