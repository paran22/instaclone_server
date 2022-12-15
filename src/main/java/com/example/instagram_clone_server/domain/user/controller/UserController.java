package com.example.instagram_clone_server.domain.user.controller;

import com.example.instagram_clone_server.common.ApiResponse;
import com.example.instagram_clone_server.domain.user.model.User;
import com.example.instagram_clone_server.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;
import org.springframework.web.bind.annotation.*;

@Tag(name = "users", description = "회원 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Operation(summary = "signup")
    @PostMapping("/signup")
    public ApiResponse<UserResponse> signUp(@RequestBody UserSignUpRequest signUpRequest) {
        return ApiResponse.success(userService.signUp(signUpRequest));
    }

    @Operation(summary = "valid email and username")
    @GetMapping("/valid")
    public ApiResponse<UserValidCheckResponse> validateEmail(@ModelAttribute UserValidCheckRequest validCheckRequest) {
        return ApiResponse.success(userService.validate(validCheckRequest));
    }

    @Getter
    @Setter
    public static class UserSignUpRequest {
        private String email;
        private String name;
        private String userName;
        private String password;

        public void encodePassword(String encodedPassword) {
            this.password = encodedPassword;
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class UserValidCheckRequest {
        private String email;
        private String userName;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserValidCheckResponse {
        private boolean isValidEmail;
        private boolean isValidUserName;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserResponse {
        private Long id;
        private String name;
        private String userName;
        private String description;
        private String link;
        private String userImage;

        public static UserResponse of(User user) {
            return UserResponse.builder()
                    .id(user.getUserId())
                    .name(user.getName())
                    .userName(user.getUserName())
                    .description(user.getDescription())
                    .link(user.getLink())
                    .userImage(user.getUserImage())
                    .build();
        }
    }
}
