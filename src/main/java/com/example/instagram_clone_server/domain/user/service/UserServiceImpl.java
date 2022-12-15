package com.example.instagram_clone_server.domain.user.service;

import com.example.instagram_clone_server.domain.user.model.User;
import com.example.instagram_clone_server.domain.user.repository.UserRepository;
import com.example.instagram_clone_server.exception.CustomException;
import com.example.instagram_clone_server.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.instagram_clone_server.domain.user.controller.UserController.*;
import static com.example.instagram_clone_server.domain.user.controller.UserController.UserResponse;
import static com.example.instagram_clone_server.domain.user.controller.UserController.UserSignUpRequest;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserResponse signUp(UserSignUpRequest signUpRequest) {
        if (!validateEmail(signUpRequest.getEmail())) {
            throw new CustomException(ErrorCode.EMAIL_IS_ALREADY_USED);
        }
        if (!validateUserName(signUpRequest.getUserName())) {
            throw new CustomException(ErrorCode.USERNAME_IS_ALREADY_USED);
        }
        signUpRequest.encodePassword(passwordEncoder.encode(signUpRequest.getPassword()));
        User user = User.of(signUpRequest);
        return UserResponse.of(userRepository.save(user));
    }

    @Override
    public UserValidCheckResponse validate(UserValidCheckRequest validCheckRequest) {
        return UserValidCheckResponse.builder()
                .isValidEmail(validateEmail(validCheckRequest.getEmail()))
                .isValidUserName(validateUserName(validCheckRequest.getUserName()))
                .build();
    }


    private boolean validateEmail(String email) {
        return !userRepository.existsAllByEmail(email);
    }

    private boolean validateUserName(String userName) {
        return !userRepository.existsAllByUserName(userName);
    }
}
