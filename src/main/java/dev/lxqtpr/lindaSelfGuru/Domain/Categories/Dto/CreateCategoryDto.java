package dev.lxqtpr.lindaSelfGuru.Domain.Categories.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCategoryDto {
    @NotNull
    private String name;

    @NotNull
    private Long userId;

    @NotNull
    private Long libraryId;
}
