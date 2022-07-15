package com.autoran.githubproxy.service;

import com.autoran.githubproxy.exception.ApiRateLimitExceededException;
import com.autoran.githubproxy.exception.InvalidAcceptHeaderException;
import com.autoran.githubproxy.exception.InvalidUserException;
import com.autoran.githubproxy.exception.OnlyForkRepositoriesException;
import com.autoran.githubproxy.model.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(InvalidAcceptHeaderException.class)
    public ResponseEntity<ApiError> handleInvalidAcceptHeaderException(final InvalidAcceptHeaderException e) {
        ApiError error = new ApiError(HttpStatus.NOT_ACCEPTABLE.value(), e.getMessage());
        log.error(error.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<ApiError> handleInvalidUserException(final InvalidUserException e) {
        ApiError error = new ApiError(HttpStatus.NOT_FOUND.value(), e.getMessage());
        log.error(error.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ApiRateLimitExceededException.class)
    public ResponseEntity<ApiError> handleApiRateLimitExceededException(final ApiRateLimitExceededException e) {
        ApiError error = new ApiError(HttpStatus.FORBIDDEN.value(), e.getMessage());
        log.error(error.getMessage());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(OnlyForkRepositoriesException.class)
    public ResponseEntity<ApiError> handleOnlyForkRepositoriesException(final OnlyForkRepositoriesException e) {
        ApiError error = new ApiError(HttpStatus.NOT_FOUND.value(), e.getMessage());
        log.error(error.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
