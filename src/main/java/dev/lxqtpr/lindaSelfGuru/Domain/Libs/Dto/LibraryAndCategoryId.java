package dev.lxqtpr.lindaSelfGuru.Domain.Libs.Dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class LibraryAndCategoryId {
    @NotNull
    private Long libraryId;

    @NotEmpty
    @NotNull
    private List<Long> categoriesId;
}
