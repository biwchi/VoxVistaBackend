package com.biwhci.vistaback.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> notValid(MethodArgumentNotValidException ex, HttpServletRequest request) {
    Map<String, String> error = new HashMap<>();
    Map<String, Object> result = new HashMap<>();

    ex.getFieldErrors().forEach(err -> error.put(err.getField(), err.getDefaultMessage()));

    result.put("status", ex.getStatusCode().value());
    result.put("error", error);

    return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
  }
}