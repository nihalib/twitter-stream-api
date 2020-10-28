package com.twitter.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "value", "author_id", "created_at", "text"})
public class DataType {

    @JsonProperty("id")
    private String id;
    @JsonProperty("value")
    private String value;
    @JsonProperty("author_id")
    private String authorId;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("text")
    private String text;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setId(String id) {
        this.id = id;
    }
}
