package dev.lxqtpr.lindaSelfGuru.Domain.Categories.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;

@Data
public class CreateCategoryDto {
    @NotNull
    private String name;

    @NotNull
    private Long libraryId;
}
