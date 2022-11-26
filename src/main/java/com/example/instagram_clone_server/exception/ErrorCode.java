package com.example.instagram_clone_server.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    //400 Bad Request

    //404 Not Found
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "404_1", "게시글을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String errorMessage;

    ErrorCode(HttpStatus httpStatus, String errorCode, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}