package com.twitter.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"ids"})
public class DeleteType {

    @JsonProperty("ids")
    private List<String> ids = null;

    public List<String> getIds() {
        return ids;
    }

    public void addIds(String id){
        if(ids == null){
            ids = new ArrayList<>();
        }
        ids.add(id);
    }

}
