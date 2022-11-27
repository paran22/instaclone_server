package com.example.instagram_clone_server.exception;

import com.example.instagram_clone_server.common.ApiResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {CustomException.class})
    public <T> ApiResponse<T> handleCustomException(CustomException ex) {
        return ApiResponse.failure(ex.getErrorCode());
    }
}