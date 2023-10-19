package dev.lxqtpr.lindaSelfGuru.Domain.Categories;

import dev.lxqtpr.lindaSelfGuru.Core.Services.FileService;
import dev.lxqtpr.lindaSelfGuru.Domain.Categories.Dto.CategoryAndSongId;
import dev.lxqtpr.lindaSelfGuru.Domain.Categories.Dto.CreateCategoryDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Categories.Dto.ResponseCategoryDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Categories.Dto.UpdateCategoryDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Libs.LibraryRepository;
import dev.lxqtpr.lindaSelfGuru.Domain.Songs.Dto.ResponseSongDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Songs.SongsRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final LibraryRepository libraryRepository;
    private final SongsRepository songsRepository;
    private final ModelMapper modelMapper;

    public ResponseCategoryDto createCategory(CreateCategoryDto dto){
        var lib = libraryRepository.findById(dto.getLibraryId())
                .orElseThrow(() -> new IllegalArgumentException("Category with this id does not exist"));
        var categoryToSave = modelMapper.map(dto, CategoryEntity.class);
        categoryToSave.setLibrary(lib);
        categoryToSave.setSongs(List.of());
        return modelMapper.map(categoryRepository.save(categoryToSave), ResponseCategoryDto.class);
    }

    public ResponseCategoryDto getCategoryById(Long categoryId){
        var category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new IllegalArgumentException("Category with this id does not exist"));
        return modelMapper.map(category, ResponseCategoryDto.class);
    }
    public List<ResponseCategoryDto> getAllCategories(){
        return categoryRepository
                .findAll()
                .stream()
                .map(category -> modelMapper.map(category, ResponseCategoryDto.class))
                .toList();
    }

    public ResponseCategoryDto addSongToCategory(CategoryAndSongId dto){
        var category = categoryRepository.findById(dto.getCategoryId()).orElseThrow();
        var song = songsRepository.findById(dto.getSongId()).orElseThrow();
        category.getSongs().add(song);
        return modelMapper.map(songsRepository.save(song), ResponseCategoryDto.class);
    }

    public ResponseCategoryDto removeSongFromCategory(CategoryAndSongId dto){
        var category = categoryRepository.findById(dto.getCategoryId()).orElseThrow();
        category.getSongs().stream().filter(song -> !Objects.equals(song.getId(), dto.getSongId()));
        return modelMapper.map(categoryRepository.save(category), ResponseCategoryDto.class);
    }
    public String deleteCategory(Long categoryId){
        categoryRepository.deleteById(categoryId);
        return "Category deleted";
    }
    public ResponseCategoryDto updateCategory(UpdateCategoryDto dto){
        var category = categoryRepository.findById(dto.getCategoryId()).orElseThrow();
        if(dto.getLibraryId() != null){
            var lib = libraryRepository.findById(dto.getLibraryId()).orElseThrow();
            category.setLibrary(lib);
        }
        category.setName(dto.getName());
        return modelMapper.map(categoryRepository.save(category), ResponseCategoryDto.class);
    }
}
