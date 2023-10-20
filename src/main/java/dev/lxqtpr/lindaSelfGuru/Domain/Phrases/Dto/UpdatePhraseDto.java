package dev.lxqtpr.lindaSelfGuru.Domain.Phrases.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdatePhraseDto {
    @NotNull
    private Long id;

    @NotNull
    private String text;

    @NotNull
    private Long time;

    @NotNull
    private Long silence;
}
