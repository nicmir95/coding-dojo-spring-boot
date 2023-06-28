package com.assignment.spring.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    void handleRuntimeException() {
        RuntimeException ex = new RuntimeException("Internal Server Error");

        ResponseEntity<ApiError> response = globalExceptionHandler.handleRuntimeException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An unexpected error occurred.", response.getBody().getMessage());

    }
    @Test
    public void handleClientNotFoundException_ShouldReturnNotFound() {
        HttpClientErrorException exception = mock(HttpClientErrorException.class);

        ResponseEntity<ApiError> responseEntity = globalExceptionHandler.handleClientNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(exception.getMessage(), responseEntity.getBody().getMessage());
    }
    @Test
    public void testHandleMissingServletRequestParameterException() {
        String parameterName = "city";
        MissingServletRequestParameterException exception = new MissingServletRequestParameterException(parameterName, "String");

        ResponseEntity<ApiError> responseEntity = globalExceptionHandler.handleMissingServletRequestParameterException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        ApiError errorResponse = responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.toString(), errorResponse.getStatus());
        assertEquals("Required parameter 'city' is missing.", errorResponse.getMessage());
    }
    @Test
    void handleRestClientException() {
        RestClientException ex = new RestClientException("Rest Client Error");

        ResponseEntity<ApiError> response = globalExceptionHandler.handleRuntimeException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error occurred while making a REST API call: " + ex.getMessage(), response.getBody().getMessage());
    }

    @Test
    void handleClientNotFoundException() {
        HttpClientErrorException ex = mock(HttpClientErrorException.class);

        ResponseEntity<ApiError> response = globalExceptionHandler.handleClientNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void handleConstraintViolationException_ShouldReturnBadRequest() {
        ConstraintViolation<?> violation = mock(ConstraintViolation.class);

        Set<ConstraintViolation<?>> violations = new HashSet<>();
        violations.add(violation);

        ConstraintViolationException exception = new ConstraintViolationException("Constraint violation", violations);

        ResponseEntity<ApiError> responseEntity = globalExceptionHandler.handleConstraintViolationException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Invalid data for fields : " + violations, responseEntity.getBody().getMessage());
    }


    @Test
    public void handleException_ShouldReturnInternalServerError() {
        Exception exception = mock(Exception.class);

        ResponseEntity<ApiError> responseEntity = globalExceptionHandler.handleException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("An error occurred: " + exception.getMessage(), responseEntity.getBody().getMessage());
    }
}
