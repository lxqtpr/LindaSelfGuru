package dev.lxqtpr.lindaSelfGuru.Domain.Songs;

import dev.lxqtpr.lindaSelfGuru.Core.Excreptions.ResourceNotFoundException;
import dev.lxqtpr.lindaSelfGuru.Core.Services.FileService;
import dev.lxqtpr.lindaSelfGuru.Domain.Categories.CategoryEntity;
import dev.lxqtpr.lindaSelfGuru.Domain.Categories.CategoryRepository;
import dev.lxqtpr.lindaSelfGuru.Domain.Songs.Dto.CreateSongDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Songs.Dto.ResponseSongDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Songs.Dto.UpdateSongDto;
import dev.lxqtpr.lindaSelfGuru.Domain.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SongService {
    private final SongsRepository songRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final FileService fileService;

    public ResponseSongDto getSongById(Long id){
        var song = songRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Song with this id does not exist"));
        return modelMapper.map(song, ResponseSongDto.class);
    }

    public ResponseSongDto createSong(CreateSongDto dto){
        var songToSave = modelMapper.map(dto, SongEntity.class);
        var fileName = fileService.upload(dto.getFile());
        var category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category with this id does not exist"));
        songToSave.setCategory(category);
        songToSave.setFileName(fileName);

        return modelMapper.map(songRepository.save(songToSave), ResponseSongDto.class);
    }

    public ResponseSongDto updateSong(UpdateSongDto dto) {
        var song = songRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Song with this id does not exist"));
        if (dto.getFile() != null){
            fileService.deleteFile(song.getFileName());
            song.setFileName(fileService.upload(dto.getFile()));
        }
        var category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category with this id does not exist"));
        song.setCategory(category);
        song.setSongName(dto.getSongName());
        return modelMapper.map(songRepository.save(song), ResponseSongDto.class);
    }
    public String deleteSong(Long id){
        var song = songRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Song with this id does not exist"));
        fileService.deleteFile(song.getFileName());
        songRepository.deleteById(id);
        return "Song was deleted";
    }
}
