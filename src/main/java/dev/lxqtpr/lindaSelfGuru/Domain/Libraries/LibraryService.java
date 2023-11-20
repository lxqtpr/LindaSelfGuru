package dev.lxqtpr.lindaSelfGuru.Domain.Libraries;

import dev.lxqtpr.lindaSelfGuru.Core.Excreptions.ResourceNotFoundException;
import dev.lxqtpr.lindaSelfGuru.Core.Services.MinioService;
import dev.lxqtpr.lindaSelfGuru.Domain.Categories.CategoryRepository;
import dev.lxqtpr.lindaSelfGuru.Domain.Categories.Dto.ResponseCategoryDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Libraries.Dto.LibraryAndCategoriesId;
import dev.lxqtpr.lindaSelfGuru.Domain.Libraries.Dto.CreateLibraryDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Libraries.Dto.ResponseLibraryDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Libraries.Dto.UpdateLibraryDto;
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
public class LibraryService {

    private final LibraryRepository libraryRepository;
    private final ModelMapper modelMapper;
    private final MinioService minioService;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Cacheable(value = "LibraryService::getLibraryById", key = "#id")
    public ResponseLibraryDto getLibraryById(Long id){
        var lib = libraryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Library with this id does not exist"));
        return modelMapper.map(lib, ResponseLibraryDto.class);
    }
    public List<ResponseLibraryDto> getAllUserLibraries(Long userId, Integer offset, Integer limit){
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with this id does not exist"));
        return user
                .getLibraries()
                .stream()
                .skip(offset)
                .limit(limit)
                .map(library -> modelMapper.map(library, ResponseLibraryDto.class))
                .toList();
    }
    public ResponseLibraryDto createLibrary(CreateLibraryDto dto){
        var libToSave = modelMapper.map(dto, LibraryEntity.class);
        var file = minioService.upload(dto.getUserId(), dto.getFile());
        var user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User with this id does not exist"));
        libToSave.setAvatar(file);
        libToSave.setUser(user);
        return modelMapper.map(libraryRepository.save(libToSave), ResponseLibraryDto.class);
    }
    public List<ResponseCategoryDto> getAllLibraryCategories(Long libraryId){
        var lib = libraryRepository.findById(libraryId)
                .orElseThrow(() -> new ResourceNotFoundException("Library with this id does not exist"));
        return lib
                .getCategories()
                .stream()
                .map(category -> modelMapper.map(category, ResponseCategoryDto.class))
                .toList();
    }
    @CacheEvict(value = "LibraryService::getLibraryById", key = "#id", allEntries = true)
    public void deleteLibrary(Long id){
        var lib = libraryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Library with this id does not exist"));

        lib.getCategories().forEach(
                category -> {
                    category.setLibrary(null);
                    categoryRepository.save(category);
                }
        );
        minioService.deleteFile(
                lib.getUser().getId(),
                lib.getAvatar()
        );
        libraryRepository.deleteById(id);
    }
    @CachePut(value = "LibraryService::getLibraryById", key = "#dto.libraryId")
    public List<ResponseCategoryDto> addCategoryToLibrary(LibraryAndCategoriesId dto){
        var library = libraryRepository.findById(dto.getLibraryId())
                .orElseThrow(() -> new ResourceNotFoundException("Library with this id does not exist"));
        var categories = categoryRepository.findAllById(dto.getCategoriesId());

        categories.forEach(category -> {
            category.setLibrary(library);
            categoryRepository.save(category);
        });

        return library
                .getCategories()
                .stream()
                .map(category -> modelMapper.map(category, ResponseCategoryDto.class))
                .toList();
    }
    @CachePut(value = "LibraryService::getLibraryById", key = "#dto.libraryId")
    public ResponseLibraryDto removeCategoryFromLibrary(LibraryAndCategoriesId dto){
        var library = libraryRepository.findById(dto.getLibraryId())
                .orElseThrow(() -> new ResourceNotFoundException("Library with this id does not exist"));
        library.getCategories()
                .forEach(category -> {
                    if (dto.getCategoriesId().contains(category.getId())){
                        category.setLibrary(null);
                        categoryRepository.save(category);
                    }
                });
        return modelMapper.map(library, ResponseLibraryDto.class);
    }
    @CachePut(value = "LibraryService::getLibraryById", key = "#dto.libraryId")
    public ResponseLibraryDto updateLibrary (UpdateLibraryDto dto){
        var library = libraryRepository.findById(dto.getLibraryId())
                .orElseThrow(() -> new ResourceNotFoundException("Library with this id does not exist"));
        if (dto.getAvatar() != null){
            var file = minioService.upload(dto.getUserId(), dto.getAvatar());
            minioService.deleteFile(
                    dto.getUserId(),
                    library.getAvatar()
            );
            library.setAvatar(file);
        }
        library.setTitle(dto.getTitle());
        library.setSubtitle(dto.getSubtitle());
        return modelMapper.map(libraryRepository.save(library), ResponseLibraryDto.class);
    }

}
