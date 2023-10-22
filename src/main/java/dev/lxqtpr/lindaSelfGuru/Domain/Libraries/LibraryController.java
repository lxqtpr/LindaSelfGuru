package dev.lxqtpr.lindaSelfGuru.Domain.Libraries;

import dev.lxqtpr.lindaSelfGuru.Domain.Categories.Dto.ResponseCategoryDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Libraries.Dto.LibraryAndCategoriesId;
import dev.lxqtpr.lindaSelfGuru.Domain.Libraries.Dto.CreateLibraryDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Libraries.Dto.ResponseLibraryDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Libraries.Dto.UpdateLibraryDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/libraries")
@RequiredArgsConstructor
public class LibraryController {
    private final LibraryService libraryService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseLibraryDto> getLibraryById(@PathVariable Long id){
        return ResponseEntity
                .ok(libraryService.getLibraryById(id));
    }
    @GetMapping("/getCategories/{id}")
    public ResponseEntity<List<ResponseCategoryDto>> getAllLibraryCategories(@PathVariable Long id){
        return ResponseEntity
                .ok(libraryService.getAllLibraryCategories(id));
    }
    @GetMapping("/getUserLibraries/{userId}")
    public ResponseEntity<List<ResponseLibraryDto>> getAllUserLibraries(@PathVariable Long userId){
        return ResponseEntity
                .ok(libraryService.getAllUserLibraries(userId));
    }

    @PostMapping
    public ResponseEntity<ResponseLibraryDto> createLibrary(@ModelAttribute @Valid CreateLibraryDto dto){
        return new ResponseEntity<>(libraryService.createLibrary(dto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void deleteLibrary(@PathVariable Long id){
       libraryService.deleteLibrary(id);
    }
    @PostMapping("/addCategory")
    public ResponseEntity<List<ResponseCategoryDto>> addCategoryToLibrary(@RequestBody @Valid LibraryAndCategoriesId dto){
        return new ResponseEntity<>(libraryService.addCategoryToLibrary(dto), HttpStatus.CREATED);
    }
    @PostMapping("/removeCategories")
    public void removeCategory(@RequestBody @Valid LibraryAndCategoriesId dto){
        libraryService.removeCategoryFromLibrary(dto);
    }

    @PutMapping
    private ResponseEntity<ResponseLibraryDto> updateLibrary(@RequestBody @Valid UpdateLibraryDto dto){
        return ResponseEntity
                .ok(libraryService.updateLibrary(dto));
    }
}
