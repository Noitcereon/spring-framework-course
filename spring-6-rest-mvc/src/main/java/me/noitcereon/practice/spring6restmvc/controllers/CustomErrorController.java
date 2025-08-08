package me.noitcereon.practice.spring6restmvc.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomErrorController {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  ResponseEntity<String> handleBindErrors(MethodArgumentNotValidException exception) {
    String errorMessage =
        exception.getFieldErrors().stream()
            .map(
                fieldError -> {
                  Map<String, String> errorMap = new HashMap<>();
                  errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
                  return errorMap;
                })
            .toList()
            .toString();
    return ResponseEntity.badRequest().body(errorMessage);
  }
}
