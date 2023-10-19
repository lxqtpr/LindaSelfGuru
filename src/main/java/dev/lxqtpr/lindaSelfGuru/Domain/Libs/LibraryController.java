package dev.lxqtpr.lindaSelfGuru.Domain.Libs;

import dev.lxqtpr.lindaSelfGuru.Domain.Categories.Dto.ResponseCategoryDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Libs.Dto.LibraryAndCategoryId;
import dev.lxqtpr.lindaSelfGuru.Domain.Libs.Dto.CreateLibraryDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Libs.Dto.ResponseLibraryDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Libs.Dto.UpdateLibraryDto;
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
    @GetMapping
    public ResponseEntity<List<ResponseLibraryDto>> getAllLibraries(){
        return ResponseEntity
                .ok(libraryService.getAllLibraries());
    }

    @PostMapping
    public ResponseEntity<ResponseLibraryDto> createLibrary(@ModelAttribute @Valid CreateLibraryDto dto){
        return new ResponseEntity<>(libraryService.createLibrary(dto), HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteLibrary/{id}")
    public ResponseEntity<String> deleteLibrary(@PathVariable Long id){
        return ResponseEntity
                .ok(libraryService.deleteLibrary(id));
    }
    @PostMapping("/addCategory")
    public ResponseEntity<ResponseLibraryDto> addCategoryToLibrary(@RequestBody @Valid LibraryAndCategoryId dto){
        return new ResponseEntity<>(libraryService.addCategoryToLibrary(dto), HttpStatus.CREATED);
    }

    @PostMapping("/removeCategory")
    public ResponseEntity<String> removeCategory(@RequestBody @Valid LibraryAndCategoryId dto){
        return ResponseEntity
                .ok(libraryService.removeCategoryFromLibrary(dto));
    }

    @PutMapping
    private ResponseEntity<ResponseLibraryDto> updateLibrary(@RequestBody @Valid UpdateLibraryDto dto){
        return ResponseEntity
                .ok(libraryService.updateLibrary(dto));
    }
}
