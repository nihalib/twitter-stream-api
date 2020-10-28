package com.twitter.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "tag"})
public class MatchingRule {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("tag")
    private Object tag;
}
