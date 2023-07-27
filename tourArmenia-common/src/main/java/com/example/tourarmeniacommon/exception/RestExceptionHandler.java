package com.example.tourarmeniacommon.exception;

import com.example.tourarmeniacommon.dto.RestErrorDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
 @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(Exception exception, WebRequest request) {
     RestErrorDto errorDto = RestErrorDto.builder()
             .statusCode(HttpStatus.NOT_FOUND.value())
             .errorMessage(exception.getMessage())
             .build();
     return handleExceptionInternal(exception, errorDto, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
 }
}
