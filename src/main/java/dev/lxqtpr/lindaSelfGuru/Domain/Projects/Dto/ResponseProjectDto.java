package dev.lxqtpr.lindaSelfGuru.Domain.Projects.Dto;

import dev.lxqtpr.lindaSelfGuru.Domain.Notes.Dto.ResponseNoteDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Songs.Dto.ResponseSongDto;
import lombok.Data;

@Data
public class ResponseProjectDto {
    private Long id;

    private String name;

    private ResponseSongDto song;

    private ResponseNoteDto note;

    private String voiceRecording;
}
