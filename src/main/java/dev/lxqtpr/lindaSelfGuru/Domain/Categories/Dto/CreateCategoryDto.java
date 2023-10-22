package dev.lxqtpr.lindaSelfGuru.Domain.Categories.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCategoryDto {
    @NotNull
    private String name;

    @NotNull
    private Long userId;

    @NotNull
    private Long libraryId;
}
