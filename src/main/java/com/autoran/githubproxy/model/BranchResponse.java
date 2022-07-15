package com.autoran.githubproxy.model;

import lombok.Builder;
import lombok.Data;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class BranchResponse {

    private String name;
    private String lastCommitSha;

    public static List<BranchResponse> mapBranches(final List<Branch> branches) {
        if (branches != null) {
            return branches.stream()
                    .map(BranchResponse::mapBranch)
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

    private static BranchResponse mapBranch(final Branch branch) {
        return BranchResponse.builder()
                .name(branch.getName())
                .lastCommitSha(branch.getCommit().getSha())
                .build();
    }
}
