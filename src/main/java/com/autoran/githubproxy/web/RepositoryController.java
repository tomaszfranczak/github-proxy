package com.autoran.githubproxy.web;

import com.autoran.githubproxy.exception.InvalidAcceptHeaderException;
import com.autoran.githubproxy.model.RepositoryResponse;
import com.autoran.githubproxy.service.GithubService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("repositories")
@AllArgsConstructor
public class RepositoryController {

    private final GithubService service;

    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> getRepositories(@PathVariable final String username, @RequestHeader(name = "Accept") String acceptHeader) {
        headerValidation(acceptHeader);
        log.info("Incoming username: {}", username);
        List<RepositoryResponse> userRepositories = service.getUserRepositories(username);
        log.info("Outgoing response for username {}: {}", username, userRepositories);
        return new ResponseEntity<>(userRepositories, HttpStatus.OK);
    }

    private void headerValidation(String acceptHeader) {
        if(!MediaType.APPLICATION_JSON_VALUE.equals(acceptHeader)) {
            throw new InvalidAcceptHeaderException();
        }
    }
}