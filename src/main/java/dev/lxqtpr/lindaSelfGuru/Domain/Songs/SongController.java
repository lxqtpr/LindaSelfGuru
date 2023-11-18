package dev.lxqtpr.lindaSelfGuru.Domain.Songs;

import dev.lxqtpr.lindaSelfGuru.Domain.Songs.Dto.CreateSongDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Songs.Dto.ResponseSongDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Songs.Dto.UpdateSongDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/songs")
@RequiredArgsConstructor
public class SongController {
    private final SongService songService;
    @GetMapping("/{id}")
    public ResponseSongDto getSongById(@PathVariable Long id){
        return songService.getSongById(id);
    }

    @PostMapping
    public ResponseSongDto createSong(@ModelAttribute @Valid CreateSongDto dto){
        return songService.createSong(dto);
    }

    @PutMapping
    public ResponseSongDto updateSong(@ModelAttribute @Valid UpdateSongDto dto){
        return songService.updateSong(dto);
    }

    @DeleteMapping("/{userId}/{songId}")
    public void deleteSong(@PathVariable Long userId, @PathVariable Long songId){
       songService.deleteSong(userId, songId);
    }
}
