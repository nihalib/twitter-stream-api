package com.twitter.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"add", "delete", "data", "meta"})
public class FilterStreamRule {

    @JsonProperty("add")
    private List<DataType> rules = null;
    @JsonProperty("delete")
    private DeleteType delete;
    @JsonProperty("data")
    private List<DataType> data = null;
    @JsonProperty("meta")
    private MetaType meta;

    public List<DataType> getAdd() {
        return rules;
    }

    public DeleteType getDelete() {
        return delete;
    }

    public List<DataType> getData() {
        return data;
    }

    public MetaType getMeta() {
        return meta;
    }

    public void setDelete(DeleteType delete) {
        this.delete = delete;
    }

    public void setData(List<DataType> data) {
        this.data = data;
    }
}
