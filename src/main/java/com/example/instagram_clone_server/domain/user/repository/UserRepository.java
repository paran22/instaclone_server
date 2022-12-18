package com.example.instagram_clone_server.domain.user.repository;

import com.example.instagram_clone_server.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsAllByPhoneNumEmail(String email);
    boolean existsAllByUserName(String userName);

    Optional<User> findUserByPhoneNumEmail(String phoneNumEmail);
    Optional<User> findUserByUserName(String userName);
}
