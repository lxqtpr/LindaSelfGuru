package dev.lxqtpr.lindaSelfGuru.Domain.Notes.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateNoteDto {
    @NotNull
    private String title;

    @NotNull
    private Long userId;
}
