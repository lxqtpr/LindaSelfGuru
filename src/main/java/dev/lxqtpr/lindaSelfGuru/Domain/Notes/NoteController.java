package dev.lxqtpr.lindaSelfGuru.Domain.Notes;

import dev.lxqtpr.lindaSelfGuru.Domain.Notes.Dto.CreateNoteDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Notes.Dto.ResponseNoteDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Notes.Dto.UpdateNoteDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @PostMapping
    public ResponseEntity<ResponseNoteDto> createNote(@RequestBody @Valid CreateNoteDto dto){
        return new ResponseEntity<>(noteService.createNote(dto), HttpStatus.CREATED);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<List<ResponseNoteDto>> getAllUserNotes(@PathVariable Long userId){
        return ResponseEntity.ok(noteService.getAllUserNotes(userId));
    }
    @PutMapping
    public ResponseEntity<ResponseNoteDto> updateNote(@RequestBody @Valid UpdateNoteDto dto){
        return ResponseEntity.ok(noteService.updateNote(dto));
    }

    @DeleteMapping("/{noteId}")
    public void deleteNote(@PathVariable Long noteId){
        noteService.deleteNote(noteId);
    }
}
