package com.autoran.githubproxy.service;

import com.autoran.githubproxy.exception.OnlyForkRepositoriesException;
import com.autoran.githubproxy.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.autoran.githubproxy.testData.TestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GithubServiceTest {

    private final GithubClient client   = mock(GithubClient.class);
    private final GithubService service = new GithubService(client);

    @Test
    void shouldReturnRepositoryResponseList() {
        //given
        List<RepositoryResponse> expectedRepositoryResponseList = List.of(createRepositoryResponse(), createRepositoryResponse());
        when(client.getRepositories(anyString())).thenReturn(createRepositoryList());
        when(client.getBranches(anyString(), anyString())).thenReturn(List.of(createBranch(), createBranch()));

        //when
        List<RepositoryResponse> actualRepositoryResponseList = service.getUserRepositories(OWNER);

        //then
        assertEquals(expectedRepositoryResponseList, actualRepositoryResponseList);
        verify(client).getRepositories(eq(OWNER));
        verify(client, times(2)).getBranches(eq(OWNER), eq(REPOSITORY_NAME));
    }

    @Test
    void shouldThrowOnlyForkRepositoriesException() {
        //given
        when(client.getRepositories(anyString())).thenReturn(List.of(createRepository(true)));

        //when & then
        assertThrows(OnlyForkRepositoriesException.class, () -> service.getUserRepositories(OWNER));
        verify(client).getRepositories(eq(OWNER));
        verifyNoMoreInteractions(client);
    }
}