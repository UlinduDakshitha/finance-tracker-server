package com.financetracker.backend.controller;
import com.financetracker.backend.dto.request.TransactionRequest;
import com.financetracker.backend.dto.response.TransactionResponse;
import com.financetracker.backend.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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
            @RequestHeader("X-User-Email") String email,
            @Valid @RequestBody TransactionRequest request
    ) {
        return ResponseEntity.ok(transactionService.createTransaction(email, request));
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getAllTransactions(
            @RequestHeader("X-User-Email") String email
    ) {
        return ResponseEntity.ok(transactionService.getAllTransactions(email));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponse> updateTransaction(
            @RequestHeader("X-User-Email") String email,
            @PathVariable Long id,
            @RequestBody TransactionRequest request
    ) {
        return ResponseEntity.ok(transactionService.updateTransaction(email, id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTransaction(
            @RequestHeader("X-User-Email") String email,
            @PathVariable Long id
    ) {
        transactionService.deleteTransaction(email, id);
        return ResponseEntity.ok("Transaction deleted successfully");
    }

    @GetMapping("/filter/type")
    public ResponseEntity<List<TransactionResponse>> filterByType(
            @RequestHeader("X-User-Email") String email,
            @RequestParam String type
    ) {
        return ResponseEntity.ok(transactionService.filterByType(email, type));
    }

    @GetMapping("/filter/category")
    public ResponseEntity<List<TransactionResponse>> filterByCategory(
            @RequestHeader("X-User-Email") String email,
            @RequestParam String categoryName
    ) {
        return ResponseEntity.ok(transactionService.filterByCategory(email, categoryName));
    }

    @GetMapping("/filter/date")
    public ResponseEntity<List<TransactionResponse>> filterByDateRange(
            @RequestHeader("X-User-Email") String email,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) {
        return ResponseEntity.ok(transactionService.filterByDateRange(email, startDate, endDate));
    }
}
