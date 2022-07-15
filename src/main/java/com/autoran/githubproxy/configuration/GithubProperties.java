package com.autoran.githubproxy.configuration;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "github")
@ConstructorBinding
@AllArgsConstructor
public class GithubProperties {

    private final String reposUrl;
    private final String branchesUrl;

    public String getReposUrl(final String username) {
        return String.format(reposUrl, username);
    }

    public String getBranchesUrl(final String username, final String repositoryName) {
        return String.format(branchesUrl, username, repositoryName);
    }
}
