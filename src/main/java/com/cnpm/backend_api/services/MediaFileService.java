package com.cnpm.backend_api.services;

import com.cnpm.backend_api.entities.MediaFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MediaFileService {
    MediaFile uploadFile(MultipartFile file, Integer projectId);
    List<MediaFile> getAllMediaFiles();
    List<MediaFile> getMediaFilesByProject(Integer projectId);
    MediaFile getMediaFileById(Integer id);
    MediaFile processFile(Integer id, String sourceLanguage, String targetLanguage);
    void deleteMediaFile(Integer id);
}
