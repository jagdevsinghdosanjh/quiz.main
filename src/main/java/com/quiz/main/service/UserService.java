package com.quiz.main.service;

import com.quiz.main.model.User;
import com.quiz.main.model.UserDto;
import com.quiz.main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerNewUser(UserDto userDto) {
        User newUser = new User();
        newUser.setUsername(userDto.getUsername());
        newUser.setPassword(userDto.getPassword()); // Storing password as-is, without encoding
        newUser.setEmail(userDto.getEmail());
        newUser.setRole("ROLE_USER"); // Assign the default role directly as a string
        return userRepository.save(newUser);
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UsernameNotFoundException("No authenticated user found");
        }
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    // Other service methods
}
