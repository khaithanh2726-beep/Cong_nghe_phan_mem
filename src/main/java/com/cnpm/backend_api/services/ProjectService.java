package com.cnpm.backend_api.services;

import com.cnpm.backend_api.dto.ProjectDTO;
import com.cnpm.backend_api.entities.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectService {
    List<Project> getAllProjects();
    Page<Project> getAllProjects(Pageable pageable);
    Project getProjectById(Integer id);
    Project createProject(ProjectDTO dto);
    Project updateProject(Integer id, ProjectDTO dto);
    void deleteProject(Integer id);
}
