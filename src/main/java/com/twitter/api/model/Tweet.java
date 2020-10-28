package com.twitter.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"data", "includes", "matching_rules"})
public class Tweet {

    @JsonProperty("data")
    private DataType data;
    @JsonProperty("includes")
    private Includes includes;
    @JsonProperty("matching_rules")
    private List<MatchingRule> matchingRules = null;
    @JsonIgnore
    private String errorCode;

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public DataType getData() {
        return data;
    }
}
