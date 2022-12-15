package com.example.instagram_clone_server.domain.user.model;

import com.example.instagram_clone_server.domain.common.model.Timestamped;
import com.example.instagram_clone_server.domain.user.controller.UserController;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.example.instagram_clone_server.domain.user.controller.UserController.*;

@Entity(name = "users")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String description;

    @Column(nullable = true)
    private String link;

    @Column(nullable = true)
    private String userImage;

    public static User of(UserSignUpRequest signUpRequest) {
        return User.builder()
                .email(signUpRequest.getEmail())
                .name(signUpRequest.getName())
                .userName(signUpRequest.getUserName())
                .password(signUpRequest.getPassword())
                .description("")
                .link("")
                .userImage("")
                .build();
    }
}
