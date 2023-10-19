package dev.lxqtpr.lindaSelfGuru.Domain.Libs.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@Data
public class CreateLibraryDto {

    @NotNull
    private String title;

    @NotNull
    private String subTitle;

    private MultipartFile file;
}
