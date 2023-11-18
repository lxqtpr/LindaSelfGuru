package dev.lxqtpr.lindaSelfGuru.Domain.Phrases;

import dev.lxqtpr.lindaSelfGuru.Domain.Phrases.Dto.CreatePhraseDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Phrases.Dto.ResponsePhraseDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Phrases.Dto.UpdatePhraseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/phrases")
@RequiredArgsConstructor
public class PhraseController {
    private final PhraseService phraseService;

    @PostMapping
    public ResponsePhraseDto createPhrase(@RequestBody @Valid CreatePhraseDto dto){
        return phraseService.createPhrase(dto);
    }

    @GetMapping("/{id}")
    public ResponsePhraseDto getPhraseById(@PathVariable Long id){
        return phraseService.getPhraseById(id);
    }

    @PutMapping
    public ResponsePhraseDto updatePhrase(@RequestBody @Valid UpdatePhraseDto dto){
        return phraseService.updatePhrase(dto);
    }

    @DeleteMapping("/{id}")
    public void deletePhase(@PathVariable Long id){
        phraseService.deletePhrase(id);
    }
}
