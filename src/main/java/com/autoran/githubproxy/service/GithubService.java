package com.autoran.githubproxy.service;

import com.autoran.githubproxy.exception.OnlyForkRepositoriesException;
import com.autoran.githubproxy.model.Repository;
import com.autoran.githubproxy.model.RepositoryResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GithubService {

    private final GithubClient client;

    public List<RepositoryResponse> getUserRepositories(final String username) {
        List<Repository> repositories = client.getRepositories(username);
        List<Repository> filteredList = filterNotForkRepositories(repositories);

        filteredList.stream()
                .parallel()
                .forEach(getBranchesForRepository(username));

        return RepositoryResponse.mapRepositories(filteredList, username);
    }

    private List<Repository> filterNotForkRepositories(List<Repository> repositories) {
        List<Repository> filteredList = repositories.stream()
                .filter(repository -> !repository.isFork())
                .collect(Collectors.toList());

        if(filteredList.isEmpty()) {
            throw new OnlyForkRepositoriesException();
        }

        return filteredList;
    }

    private Consumer<Repository> getBranchesForRepository(String username) {
        return repository -> repository.setBranches(client.getBranches(username, repository.getName()));
    }
}
