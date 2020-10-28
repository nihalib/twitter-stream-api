package com.twitter.api.service;

import com.twitter.api.model.DeleteType;
import com.twitter.api.model.FilterStreamRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
public class StreamRuleService {

    private final WebClient webClient;

    @Autowired
    public StreamRuleService(@Value("${twitter.stream.url}") String twitterURL,
                             @Value("${twitter.auth.token}") String authToken) {
        this.webClient = WebClient
                .builder()
                .baseUrl(twitterURL)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.setBearerAuth(authToken);
                    httpHeaders.setConnection("keep-alive");
                }).build();
    }

    public Mono<HttpStatus> handleRules(FilterStreamRule request){
        return webClient.post()
                .uri(uriBuilder -> uriBuilder.path("/rules").build())
                .body(Mono.just(request), FilterStreamRule.class)
                .exchange()
                .map(ClientResponse::statusCode);
    }

    public Mono<FilterStreamRule> getRules(){
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/rules").build())
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        clientResponse -> Mono.just(new HttpClientErrorException(clientResponse.statusCode())))
                .onStatus(HttpStatus::is5xxServerError,
                        clientResponse -> Mono.just(new HttpServerErrorException(clientResponse.statusCode())))
                .bodyToMono(FilterStreamRule.class);
    }

    public Mono<HttpStatus> deleteRules(String id){
        FilterStreamRule request = new FilterStreamRule();
        DeleteType deleteType = new DeleteType();
        deleteType.addIds(id);
        request.setDelete(deleteType);
        return handleRules(request);
    }
}
