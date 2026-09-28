package com.cnpm.backend_api.repositories;

import com.cnpm.backend_api.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
}
