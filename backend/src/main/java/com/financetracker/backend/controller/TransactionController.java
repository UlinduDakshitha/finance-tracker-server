package com.financetracker.backend.controller;
import com.financetracker.backend.dto.request.TransactionRequest;
import com.financetracker.backend.dto.response.TransactionResponse;
import com.financetracker.backend.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "http://localhost:3000")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(
            Authentication authentication,
            @Valid @RequestBody TransactionRequest request
    ) {
        return ResponseEntity.ok(transactionService.createTransaction(authentication.getName(), request));
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getAllTransactions(Authentication authentication) {
        return ResponseEntity.ok(transactionService.getAllTransactions(authentication.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponse> updateTransaction(
            Authentication authentication,
            @PathVariable Long id,
            @Valid @RequestBody TransactionRequest request
    ) {
        return ResponseEntity.ok(transactionService.updateTransaction(authentication.getName(), id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTransaction(
            Authentication authentication,
            @PathVariable Long id
    ) {
        transactionService.deleteTransaction(authentication.getName(), id);
        return ResponseEntity.ok("Transaction deleted successfully");
    }

    // ...existing code... (removed duplicate header-based filterByType)
    @GetMapping("/filter/type")
    public ResponseEntity<List<TransactionResponse>> filterByType(
            Authentication authentication,
            @RequestParam String type
    ) {
        return ResponseEntity.ok(transactionService.filterByType(authentication.getName(), type));
    }

    @GetMapping("/filter/category")
    public ResponseEntity<List<TransactionResponse>> filterByCategory(
            Authentication authentication,
            @RequestParam String categoryName
    ) {
        return ResponseEntity.ok(transactionService.filterByCategory(authentication.getName(), categoryName));
    }

    @GetMapping("/filter/date")
    public ResponseEntity<List<TransactionResponse>> filterByDateRange(
            Authentication authentication,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) {
        return ResponseEntity.ok(transactionService.filterByDateRange(authentication.getName(), startDate, endDate));
    }
}
