package com.financetracker.backend.repository;
import com.financetracker.backend.entity.Category;
import com.financetracker.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByUser(User user);
    List<Category> findByUserAndType(User user, String type);
    Optional<Category> findByIdAndUser(Long id, User user);
}
