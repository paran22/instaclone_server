package com.example.instagram_clone_server.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ErrorResult {
    private String errorCode;
    private String errorMessage;
    private HttpStatus httpStatus;
}