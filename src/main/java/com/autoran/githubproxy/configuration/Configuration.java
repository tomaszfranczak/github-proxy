package com.autoran.githubproxy.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@org.springframework.context.annotation.Configuration
@EnableConfigurationProperties( GithubProperties.class)
public class Configuration {

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
