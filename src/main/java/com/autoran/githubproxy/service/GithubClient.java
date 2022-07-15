package com.autoran.githubproxy.service;

import com.autoran.githubproxy.configuration.GithubProperties;
import com.autoran.githubproxy.exception.ApiRateLimitExceededException;
import com.autoran.githubproxy.exception.InvalidUserException;
import com.autoran.githubproxy.model.Branch;
import com.autoran.githubproxy.model.Repository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class GithubClient {

    private final RestTemplate restTemplate;
    private final GithubProperties properties;

    public List<Repository> getRepositories(final String username) {
        String url = properties.getReposUrl(username);
        try {
            return exchangeAsList(url, new ParameterizedTypeReference<>() {});
        } catch (HttpClientErrorException.NotFound exception) {
            throw new InvalidUserException();
        }
    }

    public List<Branch> getBranches(final String username, final String repositoryName) {
        String url = properties.getBranchesUrl(username, repositoryName);
        return exchangeAsList(url, new ParameterizedTypeReference<>() {});
    }

    private <T> List<T> exchangeAsList(String uri, ParameterizedTypeReference<List<T>> responseType) {
        try {
            log.debug("Getting {}", uri);
            return restTemplate.exchange(uri, HttpMethod.GET, null, responseType).getBody();
        } catch (HttpClientErrorException.Forbidden exception) {
            throw new ApiRateLimitExceededException();
        }
    }
}
