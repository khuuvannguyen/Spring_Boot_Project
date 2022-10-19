package com.vannguyen.SpringBootProject.configurations.exceptions;

import org.hibernate.exception.JDBCConnectionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    // ResourceNotFoundException
    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundHandling(ResourceNotFoundException exception, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail(
                new Date(),
                exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);
    }

    // ResourceExistingException
    @ExceptionHandler(value = ResourceExistingException.class)
    public ResponseEntity<?> resourceExistingHandling(ResourceExistingException exception, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail(
                new Date(),
                exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetail, HttpStatus.CONFLICT);
    }

    // BadRequestException
    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<?> badRequestHandling(BadRequestException exception, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail(
                new Date(),
                exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    // JDBCConnectionException
    @ExceptionHandler(value = JDBCConnectionException.class)
    public ResponseEntity<?> jdbcConnectionHandling(JDBCConnectionException exception, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail(
                new Date(),
                exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // GlobalException
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> globalExceptionHandling(Exception exception, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail(
                new Date(),
                exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
