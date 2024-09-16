package com.movie.prompt.model;

import lombok.Builder;

@Builder
public class MovieResponse {

    private Movie movie;
    private String movieName;
    private String breakingResp;
}
