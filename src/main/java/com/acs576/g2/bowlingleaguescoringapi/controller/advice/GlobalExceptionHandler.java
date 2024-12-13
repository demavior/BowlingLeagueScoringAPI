package com.acs576.g2.bowlingleaguescoringapi.controller.advice;

import com.acs576.g2.bowlingleaguescoringapi.dto.ErrorDetails;
import com.acs576.g2.bowlingleaguescoringapi.exception.BowlingLeagueApiRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        LOGGER.error("Something went wrong", ex);
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.UNAUTHORIZED, "User not found", ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDetails> handleBadCredentialsException(BadCredentialsException ex) {
        LOGGER.error("Something went wrong", ex);
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.UNAUTHORIZED, "Invalid credentials", ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        LOGGER.error("Something went wrong", ex);

        String wrongFields = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST, "Invalid Request Body", "Following fields are invalid : " + wrongFields);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DisabledException.class, AccountExpiredException.class, LockedException.class})
    public ResponseEntity<ErrorDetails> handleAccountStatusException(RuntimeException ex) {
        LOGGER.error("Something went wrong", ex);
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String message = ex.getMessage();
        // Customize the message or status based on the specific exception type if needed

        ErrorDetails errorDetails = new ErrorDetails(status, message, ex.getMessage());
        return new ResponseEntity<>(errorDetails, status);
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<ErrorDetails> handleInsufficientAuthenticationException(InsufficientAuthenticationException ex) {
        LOGGER.error("Something went wrong", ex);
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.FORBIDDEN, "Insufficient authentication",
                ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }

    // Handle Access Denied (403) Exceptions
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetails> handleAccessDeniedException(AccessDeniedException ex) {
        LOGGER.error("Something went wrong", ex);
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.FORBIDDEN, ex.getMessage(), ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }

    // Handle Unauthorized (401) Exceptions
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorDetails> handleAuthenticationException(AuthenticationException ex) {
        LOGGER.error("Something went wrong", ex);
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.UNAUTHORIZED, ex.getMessage(), ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BowlingLeagueApiRuntimeException.class)
    public ResponseEntity<ErrorDetails> handleBowlingLeagueApiRuntimeException(BowlingLeagueApiRuntimeException ex) {
        LOGGER.error("Something went wrong", ex);
        ErrorDetails errorDetails = new ErrorDetails(ex.getHttpStatus(), ex.getMessage(), ex.getMessage());
        return new ResponseEntity<>(errorDetails, ex.getHttpStatus());
    }

    // Handle generic exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex) {

        LOGGER.error("Something went wrong", ex);
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(),
                ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
