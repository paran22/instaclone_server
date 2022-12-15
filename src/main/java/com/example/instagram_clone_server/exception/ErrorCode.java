package com.example.instagram_clone_server.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    //400 Bad Request
    EMAIL_IS_ALREADY_USED(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다."),
    USERNAME_IS_ALREADY_USED(HttpStatus.BAD_REQUEST, "이미 존재하는 사용자이름입니다."),

    //404 Not Found
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String errorMessage;

    ErrorCode(HttpStatus httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }
}
