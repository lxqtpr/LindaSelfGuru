package dev.lxqtpr.lindaSelfGuru.Domain.Projects.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateProjectDto {

    @NotNull
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Long songId;

    @NotNull
    private Long noteId;

    @NotNull
    private MultipartFile voiceRecording;
}
