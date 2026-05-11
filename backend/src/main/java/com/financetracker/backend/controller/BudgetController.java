package com.financetracker.backend.controller;
import com.financetracker.backend.dto.request.BudgetRequest;
import com.financetracker.backend.dto.response.BudgetResponse;
import com.financetracker.backend.service.BudgetService;
import org.springframework.http.ResponseEntity;
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
            @RequestHeader("X-User-Email") String email,
            @RequestBody BudgetRequest request
    ) {
        return ResponseEntity.ok(budgetService.createBudget(email, request));
    }

    @GetMapping
    public ResponseEntity<List<BudgetResponse>> getAllBudgets(
            @RequestHeader("X-User-Email") String email
    ) {
        return ResponseEntity.ok(budgetService.getAllBudgets(email));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BudgetResponse> updateBudget(
            @RequestHeader("X-User-Email") String email,
            @PathVariable Long id,
            @RequestBody BudgetRequest request
    ) {
        return ResponseEntity.ok(budgetService.updateBudget(email, id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBudget(
            @RequestHeader("X-User-Email") String email,
            @PathVariable Long id
    ) {
        budgetService.deleteBudget(email, id);
        return ResponseEntity.ok("Budget deleted successfully");
    }
}
