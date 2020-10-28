package com.twitter.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twitter.api.model.DataType;
import com.twitter.api.model.FilterStreamRule;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@Ignore
@TestPropertySource(locations="classpath:test.properties")
public class StreamRuleServiceTest {

    public StreamRuleService streamRuleService;
    public static MockWebServer mockBackEnd;
    private ObjectMapper MAPPER = new ObjectMapper();

    @Value("${twitter.stream.url}")
    String twitterURL;
    @Value("${twitter.auth.token}")
    String authToken;

    public StreamRuleServiceTest(){
    }

    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @BeforeEach
    public void initialize() {
        streamRuleService = new StreamRuleService(twitterURL, authToken );
    }


    @Test
    public void getRules() throws Exception {
        FilterStreamRule mockRule = new FilterStreamRule();
        List<DataType> dataTypeList = new ArrayList<>();
        DataType data = new DataType();
        data.setId("123");
        dataTypeList.add(data);
        mockRule.setData(dataTypeList);

        MockResponse response = new MockResponse().setBody(MAPPER.writeValueAsString(mockRule))
                                                  .addHeader("Content-Type", "application/json");

        mockBackEnd.enqueue(response);

        Mono<FilterStreamRule> rules =  streamRuleService.getRules();

        StepVerifier.create(rules)
                .expectNextMatches(rule -> !rule.getData().isEmpty())
                .verifyComplete();

        RecordedRequest recordedRequest = mockBackEnd.takeRequest();
        assertEquals("GET", recordedRequest.getMethod());
    }

    @Test
    public void addRules() throws Exception {
        FilterStreamRule mockRule = new FilterStreamRule();
        List<DataType> dataTypeList = new ArrayList<>();
        DataType data = new DataType();
        data.setId("123");
        dataTypeList.add(data);
        mockRule.setData(dataTypeList);

        MockResponse response = new MockResponse().setBody(MAPPER.writeValueAsString(mockRule))
                .addHeader("Content-Type", "application/json");

        mockBackEnd.enqueue(response);

        Mono<HttpStatus> result =  streamRuleService.handleRules(mockRule);

        StepVerifier.create(result)
                .expectNextMatches(HttpStatus::is2xxSuccessful)
                .verifyComplete();

        RecordedRequest recordedRequest = mockBackEnd.takeRequest();
        assertEquals("POST", recordedRequest.getMethod());
    }

    @Test
    public void deleteRules() throws Exception {
        String id = "1320422843637661697";
        MockResponse response = new MockResponse().setBody(MAPPER.writeValueAsString(id))
                .addHeader("Content-Type", "application/json");

        mockBackEnd.enqueue(response);

        Mono<HttpStatus> result =  streamRuleService.deleteRules(id);

        StepVerifier.create(result)
                .expectNextMatches(HttpStatus::is2xxSuccessful)
                .verifyComplete();

        RecordedRequest recordedRequest = mockBackEnd.takeRequest();
        assertEquals("POST", recordedRequest.getMethod());
    }

}