package com.autoran.githubproxy.testData;

import org.springframework.stereotype.Component;

@Component
public class UrlBuilder {

    private static final String PATH       = "/repositories/owner";
    private static final String LOCAL_HOST = "http://localhost:";

    public String buildUrlBase(int port) {
        return LOCAL_HOST + port + PATH;
    }
}
