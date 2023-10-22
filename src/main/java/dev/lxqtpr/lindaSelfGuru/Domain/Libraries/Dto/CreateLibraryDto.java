package dev.lxqtpr.lindaSelfGuru.Domain.Libraries.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateLibraryDto {

    @NotNull
    private String title;

    @NotNull
    private String subtitle;

    @NotNull
    private Long userId;

    private MultipartFile file;
}
