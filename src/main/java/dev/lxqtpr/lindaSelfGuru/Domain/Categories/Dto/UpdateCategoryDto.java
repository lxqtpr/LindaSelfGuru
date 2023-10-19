package dev.lxqtpr.lindaSelfGuru.Domain.Categories.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCategoryDto {
    @NotNull
    private Long categoryId;

    @NotNull
    private String name;

    private Long libraryId;
}
