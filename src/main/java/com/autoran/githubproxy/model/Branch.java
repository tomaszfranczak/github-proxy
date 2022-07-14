package com.autoran.githubproxy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Branch {

    private final String name;
    private final String lastCommitSha;
}
