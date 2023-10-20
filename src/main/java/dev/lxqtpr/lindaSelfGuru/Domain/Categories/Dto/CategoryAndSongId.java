package dev.lxqtpr.lindaSelfGuru.Domain.Categories.Dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;
@Data
public class CategoryAndSongId {
    @NotNull
    private Long categoryId;

    @NotNull
    @NotEmpty
    private List<Long> songsId;
}
