package dev.lxqtpr.lindaSelfGuru.Domain.Songs.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Data
public class CreateSongDto {
    @NotNull
    private String songName;

    @NotNull
    private Long categoryId;

    private MultipartFile file;
}
