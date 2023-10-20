package dev.lxqtpr.lindaSelfGuru.Domain.Songs.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateSongDto {
    @NotNull
    private Long id;

    private MultipartFile file;

    @NotNull
    private String songName;

    @NotNull
    private Long categoryId;
}
