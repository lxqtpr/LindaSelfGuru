package dev.lxqtpr.lindaSelfGuru.Domain.Libraries.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateLibraryDto {
    @NotNull
    private Long libraryId;

    @NotNull
    private String title;

    @NotNull
    private Long userId;

    private MultipartFile avatar;

    @NotNull
    private String subtitle;
}
