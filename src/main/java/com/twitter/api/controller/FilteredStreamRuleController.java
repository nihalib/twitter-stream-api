package com.twitter.api.controller;

import com.twitter.api.model.FilterStreamRule;
import com.twitter.api.service.StreamRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/v1/rules")
public class FilteredStreamRuleController {

    private StreamRuleService streamRuleService;

    @Autowired
    public FilteredStreamRuleController(StreamRuleService streamRuleService) {
        this.streamRuleService = streamRuleService;
    }


    @PostMapping
    public ResponseEntity<String> addFilterSearchRule(@RequestBody FilterStreamRule request) {
        Mono<HttpStatus> result = streamRuleService.handleRules(request);
        return new ResponseEntity<>(result.block());
    }

    @GetMapping
    public Mono<FilterStreamRule> getFilterSearchRule(){
        return streamRuleService.getRules();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFilterSearchRule(@PathVariable String id){
        Mono<HttpStatus> result= streamRuleService.deleteRules(id);
        return new ResponseEntity<>(result.block());
    }

}
