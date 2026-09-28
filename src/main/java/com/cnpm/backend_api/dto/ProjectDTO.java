package com.cnpm.backend_api.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ProjectDTO {
    private Integer userId;
    private String title;
    private String status;
    private LocalDateTime createdAt;
}
