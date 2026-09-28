package com.cnpm.backend_api.repositories;

import com.cnpm.backend_api.entities.MediaFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MediaFileRepository extends JpaRepository<MediaFile, Integer> {
    List<MediaFile> findByProjectId(Integer projectId);
}
