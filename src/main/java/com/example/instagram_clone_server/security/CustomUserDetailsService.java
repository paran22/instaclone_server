package com.example.instagram_clone_server.security;

import com.example.instagram_clone_server.domain.user.model.User;
import com.example.instagram_clone_server.domain.user.repository.UserRepository;
import com.example.instagram_clone_server.exception.CustomException;
import com.example.instagram_clone_server.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserByPhoneNumEmailOrUserName(username);
        return new CustomUserDetailsImpl(user);

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
