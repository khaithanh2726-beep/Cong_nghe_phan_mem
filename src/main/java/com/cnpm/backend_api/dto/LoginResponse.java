package com.cnpm.backend_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private Integer id;
    private String username;
    private String email;
    private String role;
    private String token;
}
