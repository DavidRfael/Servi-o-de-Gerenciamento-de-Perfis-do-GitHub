package com.github.users.service;

import com.github.users.model.entity.User;
import com.github.users.repository.UserRepository;
import com.github.users.utils.JwtUtils;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtUtils jwtUtil;

    public AuthService(UserRepository userRepository, JwtUtils jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public String login(String login, String url) {

        User user = userRepository.findAll()
                .stream()
                .filter(u -> u.getLogin().equals(login) && u.getUrl().equals(url))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return jwtUtil.generateToken(user);
    }
}
