package dev.lxqtpr.lindaSelfGuru.Domain.Libraries.Dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class LibraryAndCategoriesId {
    @NotNull
    private Long libraryId;

    @NotEmpty
    @NotNull
    private List<Long> categoriesId;
}
