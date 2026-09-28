package com.cnpm.backend_api.services;

import com.cnpm.backend_api.dto.LoginRequest;
import com.cnpm.backend_api.dto.LoginResponse;
import com.cnpm.backend_api.dto.RegisterRequest;
import com.cnpm.backend_api.dto.RegisterResponse;

public interface UserService {
    RegisterResponse registerUser(RegisterRequest request);
    LoginResponse loginUser(LoginRequest request);
}