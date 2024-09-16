package com.movie.prompt.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Rating {

    @JsonProperty("Source")
    public String source;
    @JsonProperty("Value")
    public String value;
}
