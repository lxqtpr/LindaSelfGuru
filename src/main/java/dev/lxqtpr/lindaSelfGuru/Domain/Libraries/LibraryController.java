package dev.lxqtpr.lindaSelfGuru.Domain.Libraries;

import dev.lxqtpr.lindaSelfGuru.Domain.Categories.Dto.ResponseCategoryDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Libraries.Dto.LibraryAndCategoriesId;
import dev.lxqtpr.lindaSelfGuru.Domain.Libraries.Dto.CreateLibraryDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Libraries.Dto.ResponseLibraryDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Libraries.Dto.UpdateLibraryDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/libraries")
@RequiredArgsConstructor
public class LibraryController {
    private final LibraryService libraryService;

    @GetMapping("/{id}")
    @PreAuthorize("@securityExpression.canAccessUserToLibrary(#id)")
    public ResponseLibraryDto getLibraryById(@PathVariable Long id){
        return libraryService.getLibraryById(id);
    }
    @GetMapping("/getCategories/{id}")
    @PreAuthorize("@securityExpression.canAccessUserToLibrary(#id)")
    public List<ResponseCategoryDto> getAllLibraryCategories(
            @PathVariable Long id
    ){
        return libraryService.getAllLibraryCategories(id);
    }
    @GetMapping("/getUserLibraries/{userId}")
    public List<ResponseLibraryDto> getAllUserLibraries(
            @PathVariable Long userId,
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
            @RequestParam(value ="limit", required = false, defaultValue = "20") Integer limit){
        return libraryService.getAllUserLibraries(userId, offset, limit);
    }

    @PostMapping
    public ResponseLibraryDto createLibrary(@ModelAttribute @Valid CreateLibraryDto dto){
        return libraryService.createLibrary(dto);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("@securityExpression.canAccessUserToLibrary(#id)")
    public void deleteLibrary(@PathVariable Long id){
       libraryService.deleteLibrary(id);
    }
    @PostMapping("/addCategories")
    public List<ResponseCategoryDto> addCategoryToLibrary(@RequestBody @Valid LibraryAndCategoriesId dto){
        return libraryService.addCategoryToLibrary(dto);
    }
    @PostMapping("/removeCategories")
    @PreAuthorize("@securityExpression.canAccessUserToLibrary(#dto.libraryId)")
    public ResponseLibraryDto removeCategory(@RequestBody @Valid LibraryAndCategoriesId dto){
        return libraryService.removeCategoryFromLibrary(dto);
    }

    @PutMapping
    @PreAuthorize("@securityExpression.canAccessUserToLibrary(#dto.libraryId)")

    private ResponseLibraryDto updateLibrary(@ModelAttribute @Valid UpdateLibraryDto dto){
        return libraryService.updateLibrary(dto);
    }
}
