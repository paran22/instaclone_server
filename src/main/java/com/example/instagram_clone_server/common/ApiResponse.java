package com.example.instagram_clone_server.common;

import com.example.instagram_clone_server.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
public class ApiResponse<T> {
    private T data;
    private HttpStatus httpStatus;
    private String errorMessage;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(data, HttpStatus.OK, "");
    }

    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(null, HttpStatus.OK, "");
    }

    public static <T> ApiResponse<T> failure(ErrorCode errorCode) {
        return new ApiResponse<>(null, errorCode.getHttpStatus(), errorCode.getErrorMessage());
    }
}
