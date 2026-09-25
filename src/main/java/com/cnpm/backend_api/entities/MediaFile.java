package com.cnpm.backend_api.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "media_files")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MediaFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(name = "original_file_url")
    private String originalFileUrl;

    @Column(name = "processed_file_url")
    private String processedFileUrl;

    @Column(name = "source_language")
    private String sourceLanguage;

    @Column(name = "target_language")
    private String targetLanguage;
}