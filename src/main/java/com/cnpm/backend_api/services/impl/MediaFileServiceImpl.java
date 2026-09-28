package com.cnpm.backend_api.services.impl;

import com.cnpm.backend_api.entities.MediaFile;
import com.cnpm.backend_api.entities.Project;
import com.cnpm.backend_api.repositories.MediaFileRepository;
import com.cnpm.backend_api.repositories.ProjectRepository;
import com.cnpm.backend_api.services.MediaFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MediaFileServiceImpl implements MediaFileService {

    private static final String UPLOAD_DIR = "uploads/";
    private static final List<String> ALLOWED_EXTENSIONS =
            Arrays.asList(".mp4", ".mp3", ".avi", ".mov", ".wav", ".mkv");

    private final MediaFileRepository mediaFileRepository;
    private final ProjectRepository projectRepository;

    @Override
    public MediaFile uploadFile(MultipartFile file, Integer projectId) {
        // Tìm project
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy project với id: " + projectId));

        String originalFileName = file.getOriginalFilename();

        // Validate định dạng file
        if (originalFileName == null || ALLOWED_EXTENSIONS.stream()
                .noneMatch(ext -> originalFileName.toLowerCase().endsWith(ext))) {
            throw new RuntimeException(
                "Định dạng file không hợp lệ! Chỉ chấp nhận: " + String.join(", ", ALLOWED_EXTENSIONS));
        }

        // Tạo thư mục uploads/ nếu chưa tồn tại
        Path uploadPath = Paths.get(UPLOAD_DIR);
        try {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path filePath = uploadPath.resolve(originalFileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Ghi file thất bại: " + e.getMessage(), e);
        }

        String fileUrl = UPLOAD_DIR + originalFileName;

        MediaFile mediaFile = new MediaFile();
        mediaFile.setProject(project);
        mediaFile.setOriginalFileUrl(fileUrl);

        return mediaFileRepository.save(mediaFile);
    }

    @Override
    public List<MediaFile> getAllMediaFiles() {
        return mediaFileRepository.findAll();
    }

    @Override
    public List<MediaFile> getMediaFilesByProject(Integer projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new RuntimeException("Không tìm thấy project với id: " + projectId);
        }
        return mediaFileRepository.findByProjectId(projectId);
    }

    @Override
    public MediaFile getMediaFileById(Integer id) {
        return mediaFileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy media file với id: " + id));
    }

    @Override
    public MediaFile processFile(Integer id, String sourceLanguage, String targetLanguage) {
        MediaFile mediaFile = getMediaFileById(id);

        // Cập nhật ngôn ngữ nguồn và đích
        mediaFile.setSourceLanguage(sourceLanguage);
        mediaFile.setTargetLanguage(targetLanguage);

        // Mô phỏng đường dẫn file đã xử lý (thuyết minh đã dịch)
        String originalUrl = mediaFile.getOriginalFileUrl();
        String processedUrl = originalUrl.replace("uploads/", "uploads/processed/")
                .replace(".", "_" + targetLanguage + ".");
        mediaFile.setProcessedFileUrl(processedUrl);

        return mediaFileRepository.save(mediaFile);
    }

    @Override
    public void deleteMediaFile(Integer id) {
        if (!mediaFileRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy media file với id: " + id);
        }
        mediaFileRepository.deleteById(id);
    }
}
