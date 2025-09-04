package com.carrentalsimple.carrentportal.exception;

import com.carrentalsimple.carrentportal.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException  {

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ErrorDetails> resourceNotFoundException(ResourceNotFound rnf, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(rnf.getMessage(),
                new Date(),
                request.getDescription(true)
                );
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
        String errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");

        ErrorDetails errorDetails = new ErrorDetails(errors, new Date(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", ex.getMessage());
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntime(RuntimeException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", ex.getMessage());
        return ResponseEntity.internalServerError().body(body);
    }




    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> allExceptionHandler(Exception e, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(e.getMessage(),
                                          new Date(),
                                          request.getDescription(true)
        );

        return new ResponseEntity<>(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);

    }



}
