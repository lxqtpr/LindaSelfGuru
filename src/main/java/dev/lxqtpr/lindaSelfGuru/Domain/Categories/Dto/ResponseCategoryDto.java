package dev.lxqtpr.lindaSelfGuru.Domain.Categories.Dto;

import dev.lxqtpr.lindaSelfGuru.Domain.Songs.Dto.ResponseSongDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Songs.SongEntity;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
public class ResponseCategoryDto {
    private Long id;
    private String name;
    private List<ResponseSongDto> songs;
}
