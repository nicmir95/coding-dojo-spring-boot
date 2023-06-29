package com.assignment.spring.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


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

        log.error(errorMessage, e);
        return buildErrorResponse(HttpStatus.BAD_REQUEST, errorMessage, e);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException e) {
        String parameterName = e.getParameter().getParameterName();
        String errorMessage = "Failed to parse '" + parameterName + "' to " + e.getRequiredType();
        log.error(errorMessage, e);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage, e);
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleRuntimeException(RuntimeException e) {
        String errorMessage = "An unexpected error occurred.";
        log.error(errorMessage, e);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage, e);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiError> handleException(Exception e) {
        String errorMessage = "An error occurred: " + e.getMessage();
        log.error(errorMessage, e);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage, e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleException(MethodArgumentNotValidException e) {
        String errorMessage = "Validation for this field has failed: " + e.getParameter();
        log.error(errorMessage, e);
        return buildErrorResponse(HttpStatus.BAD_REQUEST, errorMessage, e);
    }

    @ExceptionHandler(RestClientException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiError> handleRuntimeException(RestClientException e) {
        String errorMessage = "Error occurred while making a REST API call: " + e.getMessage();
        log.error(errorMessage, e);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage, e);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiError> handleClientNotFoundException(HttpClientErrorException e) {
        String errorMessage = "Error when connecting to OpenWeather API";
        log.error(errorMessage , e.getStackTrace());
        return buildErrorResponse(HttpStatus.NOT_FOUND, errorMessage, e);
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleInvalidDataAccessApiUsageException(
            InvalidDataAccessApiUsageException e) {
        String errorMessage = "Parameter value did not match expected type";
        log.error(errorMessage, e);
        return buildErrorResponse(HttpStatus.BAD_REQUEST, errorMessage, e);    }

    @ExceptionHandler (ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ApiError> handleConstraintViolationException(
            ConstraintViolationException e) {
        String errorMessage = "Invalid data for fields : " + e.getConstraintViolations();
        log.error(errorMessage, e);
        return buildErrorResponse(HttpStatus.BAD_REQUEST, errorMessage, e);
    }

    private ResponseEntity<ApiError> buildErrorResponse(HttpStatus status, String errorMessage, Exception e) {
        ApiError errorResponse = new ApiError(status, errorMessage);
        log.error(errorMessage, e);
        return ResponseEntity.status(status).body(errorResponse);
    }
}
