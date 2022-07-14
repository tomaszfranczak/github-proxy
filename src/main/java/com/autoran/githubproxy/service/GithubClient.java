package com.autoran.githubproxy.service;

import com.autoran.githubproxy.configuration.GithubProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GithubClient {

    private final RestTemplate template;
    private final GithubProperties properties;

    public GithubClient(RestTemplate template, GithubProperties properties) {
        this.template = template;
        this.properties = properties;
    }

    public String getRepositories(final String username) {
        return template.getForObject(properties.getReposUrl(username), String.class);
    }

    public String getBranches(final String username, final String repositoryName) {
        return template.getForObject(properties.getBranchesUrl(username, repositoryName), String.class);
    }
}
