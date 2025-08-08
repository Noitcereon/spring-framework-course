package me.noitcereon.practice.spring6restmvc.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  ResponseEntity<String> handleTypeMismatch(MethodArgumentTypeMismatchException exception){
    String errorMessage = "Parameter mismatch for '%s'. The given value '%s' does not match the required type '%s'".formatted(exception.getName(), exception.getValue(), exception.getRequiredType());
    return ResponseEntity.badRequest().body(errorMessage);
  }

}
