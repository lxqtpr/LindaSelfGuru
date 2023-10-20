package dev.lxqtpr.lindaSelfGuru.Domain.Notes.Dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateNoteDto {
    @NotNull
    private Long noteId;

    @NotNull
    @NotEmpty
    private String title;
}
