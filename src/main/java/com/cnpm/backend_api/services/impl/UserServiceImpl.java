package com.cnpm.backend_api.services.impl;

import com.cnpm.backend_api.dto.LoginRequest;
import com.cnpm.backend_api.dto.LoginResponse;
import com.cnpm.backend_api.dto.RegisterRequest;
import com.cnpm.backend_api.dto.RegisterResponse;
import com.cnpm.backend_api.entities.User;
import com.cnpm.backend_api.repositories.UserRepository;
import com.cnpm.backend_api.security.JwtUtil;
import com.cnpm.backend_api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public RegisterResponse registerUser(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Tên đăng nhập đã tồn tại!");
        }

        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        newUser.setEmail(request.getEmail());
        newUser.setRole("USER");

        User savedUser = userRepository.save(newUser);

        return new RegisterResponse(
            savedUser.getId(),
            savedUser.getUsername(),
            savedUser.getEmail(),
            savedUser.getRole()
        );
    }

    @Override
    public LoginResponse loginUser(LoginRequest request) {
        // Tìm user theo username
        User user = userRepository.findByUsername(request.getUsername())
            .orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại!"));

        // So sánh mật khẩu bằng BCrypt
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Sai mật khẩu!");
        }

        // Sinh JWT token
        String token = jwtUtil.generateToken(user.getUsername());

        return new LoginResponse(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getRole(),
            token
        );
    }
}