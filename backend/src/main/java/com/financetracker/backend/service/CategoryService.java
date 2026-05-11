package com.financetracker.backend.service;
import com.financetracker.backend.dto.request.CategoryRequest;
import com.financetracker.backend.dto.response.CategoryResponse;
import com.financetracker.backend.entity.Category;
import com.financetracker.backend.entity.User;
import com.financetracker.backend.repository.CategoryRepository;
import com.financetracker.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public CategoryService(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public CategoryResponse createCategory(String email, CategoryRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Category category = Category.builder()
                .user(user)
                .name(request.getName())
                .type(request.getType())
                .createdAt(LocalDateTime.now())
                .build();

        Category saved = categoryRepository.save(category);

        return new CategoryResponse(saved.getId(), saved.getName(), saved.getType());
    }

    public List<CategoryResponse> getAllCategories(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return categoryRepository.findByUser(user)
                .stream()
                .map(c -> new CategoryResponse(c.getId(), c.getName(), c.getType()))
                .toList();
    }

    public List<CategoryResponse> getCategoriesByType(String email, String type) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return categoryRepository.findByUserAndType(user, type)
                .stream()
                .map(c -> new CategoryResponse(c.getId(), c.getName(), c.getType()))
                .toList();
    }

    public CategoryResponse updateCategory(String email, Long id, CategoryRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Category category = categoryRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setName(request.getName());
        category.setType(request.getType());

        Category updated = categoryRepository.save(category);

        return new CategoryResponse(updated.getId(), updated.getName(), updated.getType());
    }

    public void deleteCategory(String email, Long id) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Category category = categoryRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        categoryRepository.delete(category);
    }
}
