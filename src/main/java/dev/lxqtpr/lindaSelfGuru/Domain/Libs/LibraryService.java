package dev.lxqtpr.lindaSelfGuru.Domain.Libs;

import dev.lxqtpr.lindaSelfGuru.Core.Services.FileService;
import dev.lxqtpr.lindaSelfGuru.Domain.Categories.CategoryRepository;
import dev.lxqtpr.lindaSelfGuru.Domain.Categories.Dto.ResponseCategoryDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Libs.Dto.LibraryAndCategoryId;
import dev.lxqtpr.lindaSelfGuru.Domain.Libs.Dto.CreateLibraryDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Libs.Dto.ResponseLibraryDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Libs.Dto.UpdateLibraryDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LibraryService {

    private final LibraryRepository libraryRepository;
    private final ModelMapper modelMapper;
    private final FileService fileService;
    private final CategoryRepository categoryRepository;

    public ResponseLibraryDto getLibraryById(Long id){
        var lib = libraryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Library with this id does not exist"));
        return modelMapper.map(lib, ResponseLibraryDto.class);
    }
    public List<ResponseLibraryDto> getAllLibraries(){
        return libraryRepository
                .findAll()
                .stream()
                .map(library -> modelMapper.map(library, ResponseLibraryDto.class))
                .toList();
    }

    public ResponseLibraryDto createLibrary(CreateLibraryDto dto){
        var libToSave = modelMapper.map(dto, LibraryEntity.class);
        var fileName = fileService.upload(dto.getFile());
        libToSave.setAvatar(fileName);
        libToSave.setCategories(List.of());
        return modelMapper.map(libraryRepository.save(libToSave), ResponseLibraryDto.class);
    }
    public List<ResponseCategoryDto> getAllLibraryCategories(Long libraryId){
        var lib = libraryRepository.findById(libraryId).orElseThrow();
        return lib
                .getCategories()
                .stream()
                .map(category -> modelMapper.map(category, ResponseCategoryDto.class))
                .toList();
    }
    public String deleteLibrary(Long id){
        var lib = libraryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Library with this id does not exist"));
        fileService.deleteFile(lib.getAvatar());
        libraryRepository.deleteById(id);
        return "Library was deleted";
    }
    public ResponseLibraryDto addCategoryToLibrary(LibraryAndCategoryId dto){
        var library = libraryRepository.findById(dto.getLibraryId())
                .orElseThrow(() -> new IllegalArgumentException("Library with this id does not exist"));
        var category = categoryRepository.findById(dto.getCategoryId()).orElseThrow();
        library.getCategories().add(category);
        return modelMapper.map(libraryRepository.save(library), ResponseLibraryDto.class);
    }
    public String removeCategoryFromLibrary(LibraryAndCategoryId dto){
        var library = libraryRepository.findById(dto.getLibraryId())
                .orElseThrow(() -> new IllegalArgumentException("Library with this id does not exist"));
        library
                .getCategories()
                .stream()
                .filter(category -> !Objects.equals(category.getId(), dto.getCategoryId()));
        libraryRepository.save(library);
        return "Category was deleted from library";
    }
    public ResponseLibraryDto updateLibrary (UpdateLibraryDto dto){
        var library = libraryRepository.findById(dto.getId()).orElseThrow();
        if (dto.getAvatar() != null){
            var fileName = fileService.upload(dto.getAvatar());
            fileService.deleteFile(library.getAvatar());
            library.setAvatar(fileName);
        }
        library.setTitle(dto.getTitle());
        library.setSubtitle(dto.getSubtitle());
        return modelMapper.map(libraryRepository.save(library), ResponseLibraryDto.class);
    }

}
