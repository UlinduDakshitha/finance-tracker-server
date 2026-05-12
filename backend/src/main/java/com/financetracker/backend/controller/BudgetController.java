package com.financetracker.backend.controller;

import com.financetracker.backend.dto.request.BudgetRequest;
import com.financetracker.backend.dto.response.BudgetResponse;
import com.financetracker.backend.service.BudgetService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
@CrossOrigin(origins = "http://localhost:3000")
public class BudgetController {

    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @PostMapping
    public ResponseEntity<BudgetResponse> createBudget(
            Authentication authentication,
            @Valid @RequestBody BudgetRequest request
    ) {
        return ResponseEntity.ok(budgetService.createBudget(authentication.getName(), request));
    }

    @GetMapping
    public ResponseEntity<List<BudgetResponse>> getAllBudgets(Authentication authentication) {
        return ResponseEntity.ok(budgetService.getAllBudgets(authentication.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BudgetResponse> updateBudget(
            Authentication authentication,
            @PathVariable Long id,
            @Valid @RequestBody BudgetRequest request
    ) {
        return ResponseEntity.ok(budgetService.updateBudget(authentication.getName(), id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBudget(
            Authentication authentication,
            @PathVariable Long id
    ) {
        budgetService.deleteBudget(authentication.getName(), id);
        return ResponseEntity.ok("Budget deleted successfully");
    }
}