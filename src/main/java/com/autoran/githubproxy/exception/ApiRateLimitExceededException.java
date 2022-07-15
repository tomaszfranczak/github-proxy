package com.autoran.githubproxy.exception;

public class ApiRateLimitExceededException extends RuntimeException {

    private static final String MESSAGE = "API rate limit exceeded.";

    public ApiRateLimitExceededException() {
        super(MESSAGE);
    }
}
