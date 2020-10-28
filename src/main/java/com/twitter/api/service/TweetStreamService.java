package com.twitter.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twitter.api.common.TimeRangePredicate;
import com.twitter.api.model.Tweet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM;

@Service
public class TweetStreamService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private final TimeRangePredicate rangePredicate;

    public TweetStreamService(@Value("${twitter.stream.url}") String twitterURL,
                              @Value("${twitter.auth.token}") String authToken,
                              ObjectMapper objectMapper,
                              TimeRangePredicate rangePredicate){
        this.rangePredicate = rangePredicate;
        this.objectMapper = objectMapper;
        this.webClient = WebClient
                .builder()
                .baseUrl(twitterURL)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.setBearerAuth(authToken);
                    httpHeaders.setConnection("keep-alive");
                }).build();
    }

    public Flux<Tweet> tweet(){
        return webClient.get()
                .uri("?tweet.fields=created_at&expansions=author_id")
                .accept(APPLICATION_OCTET_STREAM)
                .retrieve()
                .bodyToFlux(String.class)
                .limitRate(10)
                .filter(response -> !response.isEmpty())
                .map(tweet -> {
                    System.out.println("String Tweet :::::");
                    System.out.println(tweet);
                    return objectMapper(tweet);
                })
                .filter(tweet -> rangePredicate.test(tweet.getData().getCreatedAt()))
                .map(tweet -> tweet)
                .limitRequest(20);
    }

    private Tweet objectMapper(final String request){
        try {
            return objectMapper.readValue(request, Tweet.class);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new Tweet();
    }

}
