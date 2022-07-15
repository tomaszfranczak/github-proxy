package com.autoran.githubproxy.exception;

public class InvalidUserException extends RuntimeException {

    private static final String MESSAGE = "Given username doesn't exist.";

    public InvalidUserException() {
        super(MESSAGE);
    }
}
