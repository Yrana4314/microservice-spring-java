package com.springbootproject.productservice.controlleradvice;

import com.springbootproject.productservice.exception.ProductNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<HashMap<String,Object>> handleProductNotFoundException(ProductNotFoundException ex){
        HashMap<String,Object> errorResponseMap= new HashMap();
        errorResponseMap.put("status","Error");
        errorResponseMap.put("message",ex.getMessage());
        errorResponseMap.put("Timestamp", LocalDateTime.now());
        return ResponseEntity.status(ex.getCode()).body(errorResponseMap);
    }
}
