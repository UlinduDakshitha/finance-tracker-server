package com.financetracker.backend.controller;

import com.financetracker.backend.dto.request.CategoryRequest;
import com.financetracker.backend.dto.response.CategoryResponse;
import com.financetracker.backend.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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
            @RequestHeader("X-User-Email") String email,
           @Valid @RequestBody CategoryRequest request
    ) {
        return ResponseEntity.ok(categoryService.createCategory(email, request));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories(
            @RequestHeader("X-User-Email") String email
    ) {
        return ResponseEntity.ok(categoryService.getAllCategories(email));
    }

    @GetMapping("/type")
    public ResponseEntity<List<CategoryResponse>> getCategoriesByType(
            @RequestHeader("X-User-Email") String email,
            @RequestParam String type
    ) {
        return ResponseEntity.ok(categoryService.getCategoriesByType(email, type));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @RequestHeader("X-User-Email") String email,
            @PathVariable Long id,
            @RequestBody CategoryRequest request
    ) {
        return ResponseEntity.ok(categoryService.updateCategory(email, id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(
            @RequestHeader("X-User-Email") String email,
            @PathVariable Long id
    ) {
        categoryService.deleteCategory(email, id);
        return ResponseEntity.ok("Category deleted successfully");
    }
}
