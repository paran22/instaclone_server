package com.example.instagram_clone_server.domain.user.service;

import com.example.instagram_clone_server.domain.user.model.User;
import com.example.instagram_clone_server.domain.user.repository.UserRepository;
import com.example.instagram_clone_server.exception.CustomException;
import com.example.instagram_clone_server.exception.ErrorCode;
import com.example.instagram_clone_server.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.instagram_clone_server.domain.user.controller.UserController.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    public UserResponse signUp(UserSignUpRequest signUpRequest) {
        if (!validateEmail(signUpRequest.getPhoneNumEmail())) {
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
                .isValidEmail(validateEmail(validCheckRequest.getPhoneNumOrEmail()))
                .isValidUserName(validateUserName(validCheckRequest.getUserName()))
                .build();
    }

    @Override
    public TokenResponseDto login(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = loginRequest.toAuthentication();

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        TokenResponseDto tokenDto = tokenProvider.generateTokenDto(authentication);

        User user = findUserByPhoneNumEmailOrUserName(loginRequest.getId());
        user.updateRefreshToken(tokenDto.getRefreshToken());

        return tokenDto;
    }

    @Transactional
    public TokenResponseDto reissue(TokenRequestDto tokenRequestDto) {
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new CustomException(ErrorCode.REFRESH_TOKEN_UNAUTHORIZED);
        }

        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        User user = findUserByPhoneNumEmailOrUserName(authentication.getName());
        if (!user.getRefreshToken().equals(tokenRequestDto.getRefreshToken())) {
            throw new CustomException(ErrorCode.REFRESH_TOKEN_BAD_REQUEST);
        }

        TokenResponseDto tokenDto = tokenProvider.generateTokenDto(authentication);
        user.updateRefreshToken(tokenDto.getRefreshToken());

        return tokenDto;
    }

    private boolean validateEmail(String email) {
        return !userRepository.existsAllByPhoneNumEmail(email);
    }

    private boolean validateUserName(String userName) {
        return !userRepository.existsAllByUserName(userName);
    }

    public User findUserByPhoneNumEmailOrUserName(String userName) {
        Optional<User> user = userRepository.findUserByUserName(userName);
        if (!user.isPresent()) {
            user = userRepository.findUserByPhoneNumEmail(userName);
        }
        if (!user.isPresent()) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        return user.get();
    }
}
