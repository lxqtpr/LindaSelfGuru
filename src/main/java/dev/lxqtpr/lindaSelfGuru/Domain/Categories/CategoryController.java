package dev.lxqtpr.lindaSelfGuru.Domain.Categories;

import dev.lxqtpr.lindaSelfGuru.Domain.Categories.Dto.CategoryAndSongId;
import dev.lxqtpr.lindaSelfGuru.Domain.Categories.Dto.CreateCategoryDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Categories.Dto.ResponseCategoryDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Categories.Dto.UpdateCategoryDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseCategoryDto> getCategoryById(@PathVariable Long id) {
        return ResponseEntity
                .ok(categoryService.getCategoryById(id));
    }

    @GetMapping
    public ResponseEntity<List<ResponseCategoryDto>> getAllCategories() {
        return ResponseEntity
                .ok(categoryService.getAllCategories());
    }
    @PostMapping
    public ResponseEntity<ResponseCategoryDto> createCategory(@RequestBody @Valid CreateCategoryDto dto) {
        return new ResponseEntity<>
                (categoryService.createCategory(dto), HttpStatus.CREATED);
    }

    @PostMapping("/addSongToCategory")
    public ResponseEntity<ResponseCategoryDto> addSongToCategory(
            @RequestBody @Valid CategoryAndSongId dto
    ){
        return new ResponseEntity<>
                (categoryService.addSongToCategory(dto), HttpStatus.CREATED);
    }

    @PostMapping("/removeSongFromCategory")
    public ResponseEntity<ResponseCategoryDto> removeSongFromCategory(
            @RequestBody @Valid CategoryAndSongId dto
    ){
        return new ResponseEntity<>
                (categoryService.removeSongFromCategory(dto), HttpStatus.NO_CONTENT);
    }
    @PutMapping
    private ResponseEntity<ResponseCategoryDto> updateCategory(@RequestBody @Valid UpdateCategoryDto dto){
        return ResponseEntity
                .ok(categoryService.updateCategory(dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.deleteCategory(id));
    }
}