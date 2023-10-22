package dev.lxqtpr.lindaSelfGuru.Domain.Categories.Dto;

import dev.lxqtpr.lindaSelfGuru.Domain.Songs.Dto.ResponseSongDto;
import lombok.Data;
import java.util.List;

@Data
public class ResponseCategoryDto {
    private Long id;
    private String name;
    private List<ResponseSongDto> songs;
}
