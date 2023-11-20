package dev.lxqtpr.lindaSelfGuru.Domain.Phrases;

import dev.lxqtpr.lindaSelfGuru.Core.Excreptions.ResourceNotFoundException;
import dev.lxqtpr.lindaSelfGuru.Domain.Notes.NoteRepository;
import dev.lxqtpr.lindaSelfGuru.Domain.Phrases.Dto.CreatePhraseDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Phrases.Dto.ResponsePhraseDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Phrases.Dto.UpdatePhraseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhraseService {
    private final PhraseRepository phraseRepository;
    private final NoteRepository noteRepository;
    private final ModelMapper modelMapper;

    public ResponsePhraseDto createPhrase(CreatePhraseDto dto){
        var phraseToSave = modelMapper.map(dto, PhrasesEntity.class);
        var note = noteRepository.findById(dto.getNoteId())
                .orElseThrow(() -> new ResourceNotFoundException("Note with this id does not exist"));
        phraseToSave.setNote(note);
        return modelMapper.map(phraseRepository.save(phraseToSave), ResponsePhraseDto.class);
    }

    @Cacheable(value = "PhrasesService::getPhraseById", key = "#phraseId")
    public ResponsePhraseDto getPhraseById(Long phraseId){
        var phrase = phraseRepository.findById(phraseId)
                .orElseThrow(() -> new ResourceNotFoundException("Phrase with this id does not exist"));
        return modelMapper.map(phrase, ResponsePhraseDto.class);
    }
    @CachePut(value = "PhrasesService::getPhraseById", key = "#dto.id")
    public ResponsePhraseDto updatePhrase(UpdatePhraseDto dto){
        var phrase = phraseRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Phrase with this id does not exist"));
        phrase.setText(dto.getText());
        phrase.setTime(dto.getTime());
        phrase.setSilence(dto.getSilence());
        return modelMapper.map(phraseRepository.save(phrase), ResponsePhraseDto.class);
    }

    @CacheEvict(value = "PhrasesService::getPhraseById", key = "#id", allEntries = true)
    public void deletePhrase(Long id){
        phraseRepository.deleteById(id);
    }
}
