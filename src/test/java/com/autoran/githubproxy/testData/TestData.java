package com.autoran.githubproxy.testData;

import com.autoran.githubproxy.model.*;

import java.util.List;

public class TestData {

    public static final String OWNER = "owner";
    public static final String REPOSITORY_NAME = "RepositoryName";
    public static final String BRANCH_NAME = "BranchName";
    public static final String LAST_COMMIT_SHA = "LastCommitSha";
    public static final String GET_BRANCHES_URL = "dummyGetBranchUrl";
    public static final String GET_REPOSITORIES_URL = "dummyGetRepositoriesUrl";

    public static List<Repository> createRepositoryList() {
        return List.of(createRepository(false),
                createRepository(true),
                createRepository(false));
    }

    public static RepositoryResponse createRepositoryResponse() {
        return RepositoryResponse.builder()
                .ownerLogin(OWNER)
                .repositoryName(REPOSITORY_NAME)
                .branches(List.of(createBranchResponse(), createBranchResponse()))
                .build();
    }

    public static BranchResponse createBranchResponse() {
        return BranchResponse.builder()
                .name(BRANCH_NAME)
                .lastCommitSha(LAST_COMMIT_SHA)
                .build();
    }

    public static Repository createRepository(boolean isFork) {
        return Repository.builder()
                .name(REPOSITORY_NAME)
                .fork(isFork)
                .build();
    }

    public static Branch createBranch() {
        return Branch.builder()
                .name(BRANCH_NAME)
                .commit(createCommit())
                .build();
    }

    public static Commit createCommit() {
        return Commit.builder().sha(LAST_COMMIT_SHA).build();
    }
}
