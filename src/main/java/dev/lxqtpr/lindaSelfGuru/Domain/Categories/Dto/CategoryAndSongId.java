package dev.lxqtpr.lindaSelfGuru.Domain.Categories.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CategoryAndSongId {
    @NotNull
    private Long categoryId;

    @NotNull
    private Long songId;
}
