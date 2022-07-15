package com.autoran.githubproxy.exception;

public class OnlyForkRepositoriesException extends RuntimeException {

    private static final String MESSAGE = "Given username has only fork repositories.";

    public OnlyForkRepositoriesException() {
        super(MESSAGE);
    }
}
