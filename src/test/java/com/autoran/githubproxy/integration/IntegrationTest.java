package com.autoran.githubproxy.integration;

import com.autoran.githubproxy.GithubProxyApplication;
import com.autoran.githubproxy.testData.UrlBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = { GithubProxyApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UrlBuilder urlBuilder;

    @Test
    public void shouldReturnNotAcceptableStatus() {
        //given
        final String url = urlBuilder.buildUrlBase(port);
        final String expectedResponse = readStringFromResources("api-responses/incorrect-accept-header-response.json");

        //when
        final ResponseEntity<String> responseEntity = testRestTemplate.exchange(url, HttpMethod.GET,
                new HttpEntity<>(buildHeaders(MediaType.ALL)), String.class);

        //then
        String responseEntityBody = responseEntity.getBody();
        assertEquals(expectedResponse, responseEntityBody);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
    }

    private HttpHeaders buildHeaders(final MediaType acceptHeaderValue) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(acceptHeaderValue));
        return headers;
    }

    private String readStringFromResources(final String fileName) {
        try {
            return Files.readString(Paths.get("src/test/resources/" + fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
