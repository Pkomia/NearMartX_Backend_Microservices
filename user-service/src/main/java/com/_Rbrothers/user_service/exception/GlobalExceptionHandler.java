package com._Rbrothers.user_service.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com._Rbrothers.user_service.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse>handleEmailExist(EmailAlreadyExistsException ex){
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(ErrorResponse.builder()
                                   .status(HttpStatus.BAD_REQUEST.value())
                                   .error(ex.getMessage())
                                   .timestamp(LocalDateTime.now())
                                   .build()
                                );
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse>handleInvalidCredentials(InvalidCredentialsException ex){

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .body(ErrorResponse.builder()
                                   .status(HttpStatus.UNAUTHORIZED.value())
                                   .error(ex.getMessage())
                                   .timestamp(LocalDateTime.now())
                                   .build()
                                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse>handleGeneric(Exception ex){
         
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(ErrorResponse.builder()
                                   .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                   .error(ex.getMessage())
                                   .timestamp(LocalDateTime.now())
                                   .build()
                                );
    }
}
