package com.twitter.api.controller;

import com.twitter.api.model.Tweet;
import com.twitter.api.service.TweetStreamService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

@RestController
@RequestMapping(value = "/api/v1")
public class TweetStreamController {

    private TweetStreamService tweetStreamService;

    public TweetStreamController(TweetStreamService tweetStreamService){
        this.tweetStreamService = tweetStreamService;
    }

    @GetMapping(value = "/tweets", produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<Tweet> getSearchStream() {
        return tweetStreamService.tweet();
    }
}
