package com.autoran.githubproxy.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Repository {

    private final String name;
    private final String ownerLogin;
    private final List<Branch> branches;
}
