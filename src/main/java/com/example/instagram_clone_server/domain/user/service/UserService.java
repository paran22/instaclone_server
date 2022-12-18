package com.example.instagram_clone_server.domain.user.service;

import static com.example.instagram_clone_server.domain.user.controller.UserController.*;


public interface UserService {
    UserResponse signUp(UserSignUpRequest signUpRequest);
    UserValidCheckResponse validate(UserValidCheckRequest validCheckRequest);
    TokenResponseDto login(LoginRequest loginRequest);
    TokenResponseDto reissue(TokenRequestDto tokenRequestDto);
}
