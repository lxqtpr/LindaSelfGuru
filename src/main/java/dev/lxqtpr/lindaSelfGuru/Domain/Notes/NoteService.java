package dev.lxqtpr.lindaSelfGuru.Domain.Notes;

import dev.lxqtpr.lindaSelfGuru.Core.Excreptions.ResourceNotFoundException;
import dev.lxqtpr.lindaSelfGuru.Domain.Notes.Dto.CreateNoteDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Notes.Dto.ResponseNoteDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Notes.Dto.UpdateNoteDto;
import dev.lxqtpr.lindaSelfGuru.Domain.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
        return modelMapper.map(noteRepository.save(noteToSave), ResponseNoteDto.class);
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
    public ResponseNoteDto updateNote(UpdateNoteDto dto){
        var note = noteRepository.findById(dto.getNoteId())
                .orElseThrow(() -> new ResourceNotFoundException("Note with this id does not exist"));
        note.setTitle(dto.getTitle());
        return modelMapper.map(noteRepository.save(note), ResponseNoteDto.class);
    }
    public String deleteNote(Long noteId){
        noteRepository.deleteById(noteId);
        return "Note was deleted";
    }
}
