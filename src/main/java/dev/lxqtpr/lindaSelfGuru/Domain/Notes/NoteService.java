package dev.lxqtpr.lindaSelfGuru.Domain.Notes;

import dev.lxqtpr.lindaSelfGuru.Core.Exceptions.ResourceNotFoundException;
import dev.lxqtpr.lindaSelfGuru.Domain.Notes.Dto.CreateNoteDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Notes.Dto.ResponseNoteDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Notes.Dto.UpdateNoteDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public ResponseNoteDto createNote(CreateNoteDto dto){
        var user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User with this id does not exist"));
        var noteToSave = modelMapper.map(dto, NotesEntity.class);
        noteToSave.setUser(user);
        noteToSave.setPhrases(List.of());
        return modelMapper.map(noteRepository.save(noteToSave), ResponseNoteDto.class);
    }

    @Cacheable(value = "NoteService::getNoteById", key = "#noteId")
    public ResponseNoteDto getNoteById(Long noteId){
        var note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note with this id does not exist"));
        return modelMapper.map(note, ResponseNoteDto.class);
    }
    public List<ResponseNoteDto> getAllUserNotes(Long userId){
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with this id does not exist"));
        return user
                .getNotes()
                .stream()
                .map(note -> modelMapper.map(note, ResponseNoteDto.class))
                .toList();
    }
    @CachePut(value = "NoteService::getNoteById", key = "#dto.noteId")
    public ResponseNoteDto updateNote(UpdateNoteDto dto){
        var note = noteRepository.findById(dto.getNoteId())
                .orElseThrow(() -> new ResourceNotFoundException("Note with this id does not exist"));
        note.setTitle(dto.getTitle());
        return modelMapper.map(noteRepository.save(note), ResponseNoteDto.class);
    }

    @CacheEvict(value = "NoteService::getNoteById", key = "#noteId", allEntries = true)
    public void deleteNote(Long noteId){
        noteRepository.deleteById(noteId);
    }
}
