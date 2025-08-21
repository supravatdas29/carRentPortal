package com.carrentalsimple.carrentportal.exception;

import com.carrentalsimple.carrentportal.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

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





    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> allExceptionHandler(Exception e, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(e.getMessage(),
                                          new Date(),
                                          request.getDescription(true)
        );

        return new ResponseEntity<>(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);

    }



}
