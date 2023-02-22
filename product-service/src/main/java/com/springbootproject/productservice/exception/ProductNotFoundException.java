package com.springbootproject.productservice.exception;

import lombok.Getter;

import java.io.IOException;
public class ProductNotFoundException  extends RuntimeException{
    private String message;
    @Getter
    private int code;
    public ProductNotFoundException(String message,int code){
        super(message);
        this.message = message;
        this.code = code;
    }
}
