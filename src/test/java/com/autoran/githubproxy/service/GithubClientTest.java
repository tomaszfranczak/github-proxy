package com.autoran.githubproxy.service;

import com.autoran.githubproxy.configuration.GithubProperties;
import com.autoran.githubproxy.exception.ApiRateLimitExceededException;
import com.autoran.githubproxy.exception.InvalidUserException;
import com.autoran.githubproxy.model.Branch;
import com.autoran.githubproxy.model.Repository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.autoran.githubproxy.testData.TestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class GithubClientTest {

    private final RestTemplate     restTemplate = mock(RestTemplate.class);
    private final GithubProperties properties   = mock(GithubProperties.class);
    private final GithubClient     client       = new GithubClient(restTemplate, properties);

    @Test
    void shouldReturnRepositoryList() {
        //given
        List<Repository> repositoryList = List.of(createRepository(false));
        ResponseEntity<List<Repository>> responseEntity = new ResponseEntity<>(repositoryList, HttpStatus.OK);
        when(properties.getReposUrl(anyString())).thenReturn(GET_REPOSITORIES_URL);
        when(restTemplate.exchange(anyString(), any(), any(), ArgumentMatchers.<ParameterizedTypeReference<List<Repository>>>any()))
                .thenReturn(responseEntity);

        //when
        List<Repository> resultList = client.getRepositories(OWNER);

        //then
        assertEquals(repositoryList, resultList);
        verify(properties).getReposUrl(eq(OWNER));
        verify(restTemplate).exchange(eq(GET_REPOSITORIES_URL), eq(HttpMethod.GET), eq(null), ArgumentMatchers.<ParameterizedTypeReference<List<Branch>>>any());
    }

    @Test
    void shouldReturnBranchList() {
        //given
        List<Branch> branchList = List.of(createBranch());
        ResponseEntity<List<Branch>> responseEntity = new ResponseEntity<>(branchList, HttpStatus.OK);
        when(properties.getBranchesUrl(anyString(), anyString())).thenReturn(GET_BRANCHES_URL);
        when(restTemplate.exchange(anyString(), any(), any(), ArgumentMatchers.<ParameterizedTypeReference<List<Branch>>>any()))
                .thenReturn(responseEntity);

        //when
        List<Branch> resultList = client.getBranches(OWNER, REPOSITORY_NAME);

        //then
        assertEquals(branchList, resultList);
        verify(properties).getBranchesUrl(eq(OWNER), eq(REPOSITORY_NAME));
        verify(restTemplate).exchange(eq(GET_BRANCHES_URL), eq(HttpMethod.GET), eq(null), ArgumentMatchers.<ParameterizedTypeReference<List<Branch>>>any());
    }


    @Test
    void shouldThrowInvalidUserException() {
        //given
        when(properties.getReposUrl(anyString())).thenReturn(GET_REPOSITORIES_URL);
        when(restTemplate.exchange(anyString(), any(), any(), ArgumentMatchers.<ParameterizedTypeReference<List<Branch>>>any()))
                .thenThrow(HttpClientErrorException.NotFound.class);

        //when & then
        assertThrows(InvalidUserException.class, () -> client.getRepositories(OWNER));
        verify(properties).getReposUrl(eq(OWNER));
        verify(restTemplate).exchange(eq(GET_REPOSITORIES_URL), eq(HttpMethod.GET), eq(null), ArgumentMatchers.<ParameterizedTypeReference<List<Branch>>>any());
    }

    @Test
    void shouldThrowApiRateLimitExceededException() {
        //given
        when(properties.getBranchesUrl(anyString(), anyString())).thenReturn(GET_BRANCHES_URL);
        when(restTemplate.exchange(anyString(), any(), any(), ArgumentMatchers.<ParameterizedTypeReference<List<Branch>>>any()))
                .thenThrow(HttpClientErrorException.Forbidden.class);

        //when & then
        assertThrows(ApiRateLimitExceededException.class, () -> client.getBranches(OWNER, REPOSITORY_NAME));
        verify(properties).getBranchesUrl(eq(OWNER), eq(REPOSITORY_NAME));
        verify(restTemplate).exchange(eq(GET_BRANCHES_URL), eq(HttpMethod.GET), eq(null), ArgumentMatchers.<ParameterizedTypeReference<List<Branch>>>any());
    }
}