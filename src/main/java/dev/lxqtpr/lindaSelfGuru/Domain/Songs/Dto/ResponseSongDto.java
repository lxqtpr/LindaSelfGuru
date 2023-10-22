package dev.lxqtpr.lindaSelfGuru.Domain.Songs.Dto;

import lombok.Data;
import java.util.List;

@Data
public class ResponseSongDto {
    private Long id;
    private String songName;
    private String filePath;
}
