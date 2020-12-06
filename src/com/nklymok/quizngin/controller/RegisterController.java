package com.nklymok.quizngin.controller;

import com.nklymok.quizngin.model.User;
import com.nklymok.quizngin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/register")
public class RegisterController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public void registerUser(@RequestBody User user) {
        if (checkEmail(user.getEmail()) && checkPassword(user.getPassword())) {
            if (!userRepository.existsUserByEmail(user.getEmail())) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepository.save(user);
                return;
            }
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    private boolean checkEmail(String email) {
        return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    private boolean checkPassword(String password) {
        return password.length() >= 5;
    }

}
