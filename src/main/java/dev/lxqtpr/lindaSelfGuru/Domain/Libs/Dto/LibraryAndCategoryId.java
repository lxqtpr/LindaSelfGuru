package dev.lxqtpr.lindaSelfGuru.Domain.Libs.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LibraryAndCategoryId {
    @NotNull
    private Long libraryId;

    @NotNull
    private Long categoryId;
}
