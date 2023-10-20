package dev.lxqtpr.lindaSelfGuru.Domain.Phrases.Dto;

import lombok.Data;

@Data
public class ResponsePhraseDto {
    private Long id;

    private String text;

    private Long time;

    private Long silence;
}
