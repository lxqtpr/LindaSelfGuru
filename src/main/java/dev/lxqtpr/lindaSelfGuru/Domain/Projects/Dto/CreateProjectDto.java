package dev.lxqtpr.lindaSelfGuru.Domain.Projects.Dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateProjectDto {
    @NotEmpty
    @NotNull
    private String name;

    @NotNull
    private Long songId;

    @NotNull
    private Long noteId;

    @NotNull
    private Long userId;

    private MultipartFile voiceRecording;
}
