package com.financetracker.backend.controller;

import com.financetracker.backend.dto.request.CategoryRequest;
import com.financetracker.backend.dto.response.CategoryResponse;
import com.financetracker.backend.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:3000")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(
            Authentication authentication,
            @Valid @RequestBody CategoryRequest request
    ) {
        return ResponseEntity.ok(categoryService.createCategory(authentication.getName(), request));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories(Authentication authentication) {
        return ResponseEntity.ok(categoryService.getAllCategories(authentication.getName()));
    }

    @GetMapping("/type")
    public ResponseEntity<List<CategoryResponse>> getCategoriesByType(
            Authentication authentication,
            @RequestParam String type
    ) {
        return ResponseEntity.ok(categoryService.getCategoriesByType(authentication.getName(), type));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(
            Authentication authentication,
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest request
    ) {
        return ResponseEntity.ok(categoryService.updateCategory(authentication.getName(), id, request));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(
            Authentication authentication,
            @PathVariable Long id
    ) {
        categoryService.deleteCategory(authentication.getName(), id);
        return ResponseEntity.ok("Category deleted successfully");
    }
}
