package com.cnpm.backend_api.services.impl;

import com.cnpm.backend_api.dto.ProjectDTO;
import com.cnpm.backend_api.entities.Project;
import com.cnpm.backend_api.entities.User;
import com.cnpm.backend_api.repositories.ProjectRepository;
import com.cnpm.backend_api.repositories.UserRepository;
import com.cnpm.backend_api.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Page<Project> getAllProjects(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }

    @Override
    public Project getProjectById(Integer id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy project với id: " + id));
    }

    @Override
    public Project createProject(ProjectDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user với id: " + dto.getUserId()));

        Project project = new Project();
        project.setUser(user);
        project.setTitle(dto.getTitle());
        project.setStatus(dto.getStatus() != null ? dto.getStatus() : "ACTIVE");
        project.setCreatedAt(dto.getCreatedAt());

        return projectRepository.save(project);
    }

    @Override
    public Project updateProject(Integer id, ProjectDTO dto) {
        Project project = getProjectById(id);

        if (dto.getTitle() != null) project.setTitle(dto.getTitle());
        if (dto.getStatus() != null) project.setStatus(dto.getStatus());

        return projectRepository.save(project);
    }

    @Override
    public void deleteProject(Integer id) {
        if (!projectRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy project với id: " + id);
        }
        projectRepository.deleteById(id);
    }
}
