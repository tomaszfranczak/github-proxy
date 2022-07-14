package com.autoran.githubproxy.service;

import com.autoran.githubproxy.model.ApiError;
import com.autoran.githubproxy.exception.InvalidAcceptHeaderException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(InvalidAcceptHeaderException.class)
    public ResponseEntity<ApiError> handleException() {
        ApiError error = new ApiError(HttpStatus.NOT_ACCEPTABLE.value(), "Incorrect accept header value.");
        log.error(error.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
    }
}
