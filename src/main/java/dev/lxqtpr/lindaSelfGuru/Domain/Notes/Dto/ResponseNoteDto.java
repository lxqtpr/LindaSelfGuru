package dev.lxqtpr.lindaSelfGuru.Domain.Notes.Dto;

import dev.lxqtpr.lindaSelfGuru.Domain.Phrases.Dto.ResponsePhraseDto;
import lombok.Data;

import java.util.List;

@Data
public class ResponseNoteDto {
    private Long id;
    private String title;
    private List<ResponsePhraseDto> phrases;
}
