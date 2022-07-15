package com.autoran.githubproxy.exception;

public class InvalidAcceptHeaderException extends RuntimeException {

    private static final String MESSAGE = "Incorrect accept header value.";

    public InvalidAcceptHeaderException() {
        super(MESSAGE);
    }
}
