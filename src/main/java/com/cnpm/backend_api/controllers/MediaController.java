package com.cnpm.backend_api.controllers;

import com.cnpm.backend_api.entities.MediaFile;
import com.cnpm.backend_api.services.MediaFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/media")
@RequiredArgsConstructor
@Tag(name = "Media Files", description = "API quản lý và xử lý tệp tin đa phương tiện")
@SecurityRequirement(name = "bearerAuth")
public class MediaController {

    private final MediaFileService mediaFileService;

    // POST /api/media/upload
    @Operation(summary = "Upload file media (mp4, mp3, avi, mov, wav, mkv)")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MediaFile> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("projectId") Integer projectId) {
        return ResponseEntity.ok(mediaFileService.uploadFile(file, projectId));
    }

    // GET /api/media
    @Operation(summary = "Lấy danh sách tất cả media file")
    @GetMapping
    public ResponseEntity<List<MediaFile>> getAllMediaFiles() {
        return ResponseEntity.ok(mediaFileService.getAllMediaFiles());
    }

    // GET /api/media/project/{projectId}
    @Operation(summary = "Lấy danh sách media file theo project")
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<MediaFile>> getMediaFilesByProject(@PathVariable Integer projectId) {
        return ResponseEntity.ok(mediaFileService.getMediaFilesByProject(projectId));
    }

    // GET /api/media/{id}
    @Operation(summary = "Lấy chi tiết media file theo ID")
    @GetMapping("/{id}")
    public ResponseEntity<MediaFile> getMediaFileById(@PathVariable Integer id) {
        return ResponseEntity.ok(mediaFileService.getMediaFileById(id));
    }

    // POST /api/media/{id}/process
    @Operation(summary = "Xử lý thuyết minh tự động — gán ngôn ngữ nguồn, ngôn ngữ đích, sinh URL file đã xử lý")
    @PostMapping("/{id}/process")
    public ResponseEntity<MediaFile> processFile(
            @PathVariable Integer id,
            @RequestParam("sourceLanguage") String sourceLanguage,
            @RequestParam("targetLanguage") String targetLanguage) {
        return ResponseEntity.ok(mediaFileService.processFile(id, sourceLanguage, targetLanguage));
    }

    // DELETE /api/media/{id}
    @Operation(summary = "Xóa media file")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMediaFile(@PathVariable Integer id) {
        mediaFileService.deleteMediaFile(id);
        return ResponseEntity.ok("Xóa media file thành công!");
    }
}
