package com.cnpm.backend_api.controllers;

import com.cnpm.backend_api.dto.ProjectDTO;
import com.cnpm.backend_api.entities.Project;
import com.cnpm.backend_api.services.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@Tag(name = "Projects", description = "API quản lý dự án")
@SecurityRequirement(name = "bearerAuth")
public class ProjectController {

    private final ProjectService projectService;

    // GET /api/projects?page=0&size=10
    @Operation(summary = "Lấy danh sách tất cả project (có phân trang)")
    @GetMapping
    public ResponseEntity<?> getAllProjects(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "false") boolean all) {

        if (all) {
            List<Project> projects = projectService.getAllProjects();
            return ResponseEntity.ok(projects);
        }
        Page<Project> projects = projectService.getAllProjects(
                PageRequest.of(page, size, Sort.by("id").descending()));
        return ResponseEntity.ok(projects);
    }

    // GET /api/projects/{id}
    @Operation(summary = "Lấy project theo ID")
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Integer id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    // POST /api/projects
    @Operation(summary = "Tạo project mới")
    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody ProjectDTO dto) {
        return ResponseEntity.ok(projectService.createProject(dto));
    }

    // PUT /api/projects/{id}
    @Operation(summary = "Cập nhật thông tin project")
    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Integer id,
                                                  @RequestBody ProjectDTO dto) {
        return ResponseEntity.ok(projectService.updateProject(id, dto));
    }

    // DELETE /api/projects/{id}
    @Operation(summary = "Xóa project")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable Integer id) {
        projectService.deleteProject(id);
        return ResponseEntity.ok("Xóa project thành công!");
    }
}
