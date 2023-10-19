package dev.lxqtpr.lindaSelfGuru.Domain.Songs;

import dev.lxqtpr.lindaSelfGuru.Domain.Songs.Dto.CreateSongDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Songs.Dto.ResponseSongDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Songs.Dto.UpdateSongDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/songs")
@RequiredArgsConstructor
public class SongController {
    private final SongService songService;
    @GetMapping("/{id}")
    public ResponseEntity<ResponseSongDto> getSongById(@PathVariable Long id){
        return ResponseEntity.ok(songService.getSongById(id));
    }

    @PostMapping
    public ResponseEntity<ResponseSongDto> createSong(@ModelAttribute @Valid CreateSongDto dto){
        return new ResponseEntity<>(songService.createSong(dto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ResponseSongDto> updateSong(@ModelAttribute @Valid UpdateSongDto dto){
        return ResponseEntity.ok(songService.updateSong(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSong(@PathVariable Long id){
        return ResponseEntity.ok(songService.deleteSong(id));
    }
}
