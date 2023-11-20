package dev.lxqtpr.lindaSelfGuru.Domain.Categories;

import dev.lxqtpr.lindaSelfGuru.Domain.Categories.Dto.CategoryAndSongId;
import dev.lxqtpr.lindaSelfGuru.Domain.Categories.Dto.CreateCategoryDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Categories.Dto.ResponseCategoryDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Categories.Dto.UpdateCategoryDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/{id}")
    @PreAuthorize("@securityExpression.canAccessUserToCategory(#id)")
    public ResponseCategoryDto getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @GetMapping("/getUserCategories/{userId}")
    public List<ResponseCategoryDto> getAllCategories(@PathVariable Long userId) {
        return categoryService.getAllUserCategories(userId);
    }
    @PostMapping
    public ResponseCategoryDto createCategory(@RequestBody @Valid CreateCategoryDto dto) {
        return categoryService.createCategory(dto);
    }

    @PostMapping("/addSongToCategory")
    @PreAuthorize("@securityExpression.canAccessUserToCategory(#dto.categoryId)")
    public ResponseCategoryDto addSongToCategory(
            @RequestBody @Valid CategoryAndSongId dto
    ){
        return categoryService.addSongToCategory(dto);
    }

    @PostMapping("/removeSongFromCategory")
    @PreAuthorize("@securityExpression.canAccessUserToCategory(#dto.categoryId)")
    public ResponseCategoryDto removeSongFromCategory(@RequestBody @Valid CategoryAndSongId dto){
        return categoryService.removeSongFromCategory(dto);
    }
    @PutMapping
    @PreAuthorize("@securityExpression.canAccessUserToCategory(#dto.categoryId)")
    private ResponseCategoryDto updateCategory(@RequestBody @Valid UpdateCategoryDto dto){
        return categoryService.updateCategory(dto);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("@securityExpression.canAccessUserToCategory(#id)")
    public void deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
    }
}