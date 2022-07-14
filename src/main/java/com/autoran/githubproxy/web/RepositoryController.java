package com.autoran.githubproxy.web;

import java.util.List;

import com.autoran.githubproxy.exception.InvalidAcceptHeaderException;
import com.autoran.githubproxy.model.Branch;
import com.autoran.githubproxy.model.Repository;
import com.autoran.githubproxy.service.GithubClient;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("repositories")
@AllArgsConstructor
public class RepositoryController {

    private final GithubClient client;

    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> getRepositories(@PathVariable final String username, @RequestHeader(name = "Accept") String acceptHeader) {
        if(!MediaType.APPLICATION_JSON_VALUE.equals(acceptHeader)) {
            throw new InvalidAcceptHeaderException();
        }
        System.out.println(client.getRepositories("defunkt"));
        return new ResponseEntity<>(findBookById(username), HttpStatus.OK);
    }

    private List<Repository> findBookById(String username) {
        System.out.println(username);
        return List.of(createData(), createData());
    }

    private Repository createData() {
        return new Repository("name", "ownerLogin", List.of(createBranch(), createBranch()));
    }

    private Branch createBranch() {
        return Branch.builder()
                .name("branch1")
                .lastCommitSha("lastCommitHash").build();
    }
}