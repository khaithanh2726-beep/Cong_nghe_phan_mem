package com.cnpm.backend_api.services.impl;

import com.cnpm.backend_api.dto.RegisterRequest;
import com.cnpm.backend_api.entities.User;
import com.cnpm.backend_api.repositories.UserRepository;
import com.cnpm.backend_api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User registerUser(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Tên đăng nhập đã tồn tại!");
        }

        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPasswordHash(request.getPassword()); 
        newUser.setEmail(request.getEmail());
        newUser.setRole("USER");

        return userRepository.save(newUser);
    }
}