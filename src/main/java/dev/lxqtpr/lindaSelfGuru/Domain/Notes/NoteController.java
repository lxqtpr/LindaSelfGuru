package dev.lxqtpr.lindaSelfGuru.Domain.Notes;

import dev.lxqtpr.lindaSelfGuru.Domain.Notes.Dto.CreateNoteDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Notes.Dto.ResponseNoteDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Notes.Dto.UpdateNoteDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @PostMapping
    public ResponseNoteDto createNote(@RequestBody @Valid CreateNoteDto dto){
        return noteService.createNote(dto);
    }
    @GetMapping("/{noteId}")
    @PreAuthorize("@securityExpression.canAccessUserToNote(#noteId)")
    public ResponseNoteDto getNoteById(@PathVariable Long noteId){
        return noteService.getNoteById(noteId);
    }
    @GetMapping("getNoteByUserId/{userId}")
    public List<ResponseNoteDto> getAllUserNotes(@PathVariable Long userId){
        return noteService.getAllUserNotes(userId);
    }
    @PutMapping
    @PreAuthorize("@securityExpression.canAccessUserToNote(#dto.noteId)")
    public ResponseNoteDto updateNote(@RequestBody @Valid UpdateNoteDto dto){
        return noteService.updateNote(dto);
    }

    @DeleteMapping("/{noteId}")
    @PreAuthorize("@securityExpression.canAccessUserToNote(#noteId)")
    public void deleteNote(@PathVariable Long noteId){
        noteService.deleteNote(noteId);
    }
}
