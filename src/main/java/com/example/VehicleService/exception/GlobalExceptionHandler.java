//package com.example.VehicleService.exception;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.Map;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//    // Handles ResourceNotFoundException
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<?> handleNotFound(ResourceNotFoundException ex) {
//        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
//    }
//
//    // Generic fallback handler for all other exceptions
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<?> handleAllExceptions(Exception ex) {
//        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong: " + ex.getMessage());
//    }
//
//    // Reusable method to build error response
//    private ResponseEntity<?> buildResponse(HttpStatus status, String message) {
//        Map<String, Object> error = new HashMap<>();
//        error.put("timestamp", LocalDateTime.now());
//        error.put("status", status.value());
//        error.put("error", status.getReasonPhrase());
//        error.put("message", message);
//        return new ResponseEntity<>(error, status);
//    }
//}
