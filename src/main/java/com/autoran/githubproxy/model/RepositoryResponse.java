package com.autoran.githubproxy.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class RepositoryResponse {

    private String repositoryName;
    private String ownerLogin;
    private List<BranchResponse> branches;

    public static List<RepositoryResponse> mapRepositories(final List<Repository> repositories, final String username) {
        return repositories.stream()
                .map(repository -> mapRepository(repository, username))
                .collect(Collectors.toList());
    }

    private static RepositoryResponse mapRepository(final Repository repository, final String username) {
        return RepositoryResponse.builder()
                .repositoryName(repository.getName())
                .ownerLogin(username)
                .branches(BranchResponse.mapBranches(repository.getBranches()))
                .build();
    }
}
