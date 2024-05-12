package com.auth.sessionandcookie.exception;

import com.auth.sessionandcookie.dto.response.ResponseDTO;
import com.auth.sessionandcookie.exception.exceptions.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    // Handle Known Exceptions and return response accordingly
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ResponseDTO> handleCustomBaseExceptions(BaseException ex) {
        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(ResponseDTO.generateFailureResponse(ex));
    }

    // Handle all other unknown exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO> defaultErrorHandler(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseDTO.generateFailureResponse(new BaseException()));
    }
}
