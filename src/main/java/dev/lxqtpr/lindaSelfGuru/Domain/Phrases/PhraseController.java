package dev.lxqtpr.lindaSelfGuru.Domain.Phrases;

import dev.lxqtpr.lindaSelfGuru.Domain.Phrases.Dto.CreatePhraseDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Phrases.Dto.ResponsePhraseDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Phrases.Dto.UpdatePhraseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/phrases")
@RequiredArgsConstructor
public class PhraseController {
    private final PhraseService phraseService;

    @PostMapping
    public ResponseEntity<ResponsePhraseDto> createPhrase(@RequestBody @Valid CreatePhraseDto dto){
        return new ResponseEntity<>(phraseService.createPhrase(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePhraseDto> getPhraseById(@PathVariable Long id){
        return ResponseEntity.ok(phraseService.getPhraseById(id));
    }

    @PutMapping
    public ResponseEntity<ResponsePhraseDto> updatePhrase(@RequestBody @Valid UpdatePhraseDto dto){
        return ResponseEntity.ok(phraseService.updatePhrase(dto));
    }

    @DeleteMapping("/{id}")
    public void deletePhase(@PathVariable Long id){
        phraseService.deletePhrase(id);
    }
}
