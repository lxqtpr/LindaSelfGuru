package dev.lxqtpr.lindaSelfGuru.Domain.Libs.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateLibraryDto {
    @NotNull
    private Long id;

    @NotNull
    private String title;

    private MultipartFile avatar;

    @NotNull
    private String subtitle;
}
