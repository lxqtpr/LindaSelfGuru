package dev.lxqtpr.lindaSelfGuru.Domain.Categories;

import dev.lxqtpr.lindaSelfGuru.Core.Excreptions.ResourceNotFoundException;
import dev.lxqtpr.lindaSelfGuru.Domain.Categories.Dto.CategoryAndSongId;
import dev.lxqtpr.lindaSelfGuru.Domain.Categories.Dto.CreateCategoryDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Categories.Dto.ResponseCategoryDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Categories.Dto.UpdateCategoryDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Libraries.LibraryRepository;
import dev.lxqtpr.lindaSelfGuru.Domain.Songs.SongsRepository;
import dev.lxqtpr.lindaSelfGuru.Domain.Users.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final LibraryRepository libraryRepository;
    private final SongsRepository songsRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public ResponseCategoryDto createCategory(CreateCategoryDto dto){
        var categoryToSave = modelMapper.map(dto, CategoryEntity.class);
        var lib = libraryRepository.findById(dto.getLibraryId())
                .orElseThrow(() -> new ResourceNotFoundException("Library with this id does not exist"));
        var user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User with this id does not exist"));
        categoryToSave.setLibrary(lib);
        categoryToSave.setSongs(List.of());
        categoryToSave.setUser(user);
        return modelMapper.map(categoryRepository.save(categoryToSave), ResponseCategoryDto.class);
    }

    public ResponseCategoryDto getCategoryById(Long categoryId){
        var category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category with this id does not exist"));
        return modelMapper.map(category, ResponseCategoryDto.class);
    }
    public List<ResponseCategoryDto> getAllUserCategories(Long userId){
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with this id does not exist"));
        return user.getCategories()
                .stream()
                .map(category -> modelMapper.map(category, ResponseCategoryDto.class))
                .toList();
    }

    public ResponseCategoryDto addSongToCategory(CategoryAndSongId dto){
        var category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category with this id does not exist"));
        var songs = songsRepository.findAllById(dto.getSongsId());
        songs.forEach(song -> {
            song.setCategory(category);
            songsRepository.save(song);
        });
        return modelMapper.map(categoryRepository.save(category), ResponseCategoryDto.class);
    }

    public void removeSongFromCategory(CategoryAndSongId dto){
        var category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category with this id does not exist"));

        category.getSongs()
                .forEach(song -> {
                    if (dto.getSongsId().contains(song.getId())){
                        song.setCategory(null);
                        songsRepository.save(song);
                    }
                });
    }
    public void deleteCategory(Long categoryId){
        categoryRepository.deleteById(categoryId);
    }
    public ResponseCategoryDto updateCategory(UpdateCategoryDto dto){
        var category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category with this id does not exist"));
        category.setName(dto.getName());
        return modelMapper.map(categoryRepository.save(category), ResponseCategoryDto.class);
    }
}
