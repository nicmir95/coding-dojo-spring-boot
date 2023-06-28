package com.assignment.spring.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.bind.MissingServletRequestParameterException;


import javax.validation.ConstraintViolationException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException e) {
        String parameterName = e.getParameterName();
        String errorMessage = "Required parameter '" + parameterName + "' is missing.";

        ApiError errorResponse = new ApiError(HttpStatus.BAD_REQUEST, errorMessage);
        log.error(errorMessage, e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleRuntimeException(RuntimeException e) {
        String errorMessage = "An unexpected error occurred.";
        ApiError errorResponse = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
        log.error(errorMessage, e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiError> handleException(Exception e) {
        String errorMessage = "An error occurred: " + e.getMessage();
        ApiError errorResponse = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
        log.error(errorMessage, e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleException(MethodArgumentNotValidException e) {
        String errorMessage = "Validation for this field has failed: " + e.getParameter();
        log.error(errorMessage, e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(HttpStatus.BAD_REQUEST, errorMessage));
    }

    @ExceptionHandler(RestClientException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiError> handleRuntimeException(RestClientException e) {
        String errorMessage = "Error occurred while making a REST API call: " + e.getMessage();
        log.error(errorMessage, e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage));
    }

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiError> handleClientNotFoundException(HttpClientErrorException e) {
        log.error("Error when connecting to OpenWeather API", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiError(HttpStatus.NOT_FOUND, e.getMessage()));
    }


    @ExceptionHandler (ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ApiError> handleConstraintViolationException(
            ConstraintViolationException e) {
        String errorMessage = "Invalid data for fields : " + e.getConstraintViolations();
        log.error(errorMessage, e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(HttpStatus.BAD_REQUEST, errorMessage));
    }
}
