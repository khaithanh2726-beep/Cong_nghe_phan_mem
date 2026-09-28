package com.cnpm.backend_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterResponse {
    private Integer id;
    private String username;
    private String email;
    private String role;
}
