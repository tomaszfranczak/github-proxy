package com.autoran.githubproxy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Repository {

    private String name;
    private boolean fork;
    private List<Branch> branches;
}
