package com.example.instagram_clone_server.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    //400 Bad Request
    EMAIL_IS_ALREADY_USED(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다."),
    USERNAME_IS_ALREADY_USED(HttpStatus.BAD_REQUEST, "이미 존재하는 사용자이름입니다."),
    USERNAME_OR_PASSWORD_BAD_REQUEST(HttpStatus.BAD_REQUEST, "id 혹은 비밀번호가 일치하지 않습니다."),
    REFRESH_TOKEN_BAD_REQUEST(HttpStatus.BAD_REQUEST, "refresh token이 일치하지 않습니다."),

    //401 Unauthorized
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."),
    ACCESS_TOKEN_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "유효하지 않은 access token입니다."),
    REFRESH_TOKEN_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "유효하지 않은 refresh token입니다."),


    //404 Not Found
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."),
    JWT_NOT_FOUND(HttpStatus.NOT_FOUND, "jwt 토큰이 존재하지 않습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String errorMessage;

    ErrorCode(HttpStatus httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }
}
