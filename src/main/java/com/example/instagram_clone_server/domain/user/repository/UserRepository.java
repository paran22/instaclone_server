package com.example.instagram_clone_server.domain.user.repository;

import com.example.instagram_clone_server.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsAllByEmail(String email);
    boolean existsAllByUserName(String userName);
}
