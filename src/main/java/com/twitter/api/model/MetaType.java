package com.twitter.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"sent"})
public class MetaType {

    @JsonProperty("sent")
    private String sent;

    @JsonProperty("sent")
    public String getSent() {
        return sent;
    }
}
