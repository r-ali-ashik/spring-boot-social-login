package com.rali.exception.handler;

import com.rali.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {BadCredentialsException.class})
    protected ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex, WebRequest webRequest) {
        Map<String, Object> error = new HashMap<>();
        error.put("message", "Invalid username or password.");
        error.put("status", HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(value = {ApiException.class})
    protected ResponseEntity<Object> handleApiException(ApiException ex, WebRequest webRequest) {


        String message = ex.getMessage();
        HttpStatus httpStatus = ex.getHttpStatus();
        if (httpStatus.value() >= 500) {
            log.error("Error", ex);
        }

        Map<String, Object> error = new HashMap<>();
        error.put("message", message);
        error.put("status", httpStatus.value());
        return ResponseEntity.status(httpStatus).body(error);
    }
}
