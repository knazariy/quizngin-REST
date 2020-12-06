package com.nklymok.quizngin.service;

import com.nklymok.quizngin.model.User;
import com.nklymok.quizngin.repository.UserRepository;
import com.nklymok.quizngin.security.QuizUserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class QuizUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public QuizUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() ->
                new RuntimeException("User not found in com.nklymok.quizngin.repository"));
        return new QuizUserPrincipal(user);
    }

}
