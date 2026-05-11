package com.financetracker.backend.service;
import com.financetracker.backend.dto.request.TransactionRequest;
import com.financetracker.backend.dto.response.TransactionResponse;
import com.financetracker.backend.entity.Category;
import com.financetracker.backend.entity.Transaction;
import com.financetracker.backend.entity.User;
import com.financetracker.backend.repository.CategoryRepository;
import com.financetracker.backend.repository.TransactionRepository;
import com.financetracker.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public TransactionService(TransactionRepository transactionRepository,
                              UserRepository userRepository,
                              CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public TransactionResponse createTransaction(String email, TransactionRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Category category = categoryRepository.findByUserAndType(user, request.getType())
                .stream()
                .filter(c -> c.getName().equalsIgnoreCase(request.getCategoryName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Transaction transaction = Transaction.builder()
                .user(user)
                .category(category)
                .title(request.getTitle())
                .amount(request.getAmount())
                .type(request.getType())
                .transactionDate(request.getTransactionDate())
                .note(request.getNote())
                .createdAt(LocalDateTime.now())
                .build();

        Transaction saved = transactionRepository.save(transaction);

        return new TransactionResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getAmount(),
                saved.getCategory().getName(),
                saved.getType(),
                saved.getTransactionDate(),
                saved.getNote()
        );
    }

    public List<TransactionResponse> getAllTransactions(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return transactionRepository.findByUser(user)
                .stream()
                .map(t -> new TransactionResponse(
                        t.getId(),
                        t.getTitle(),
                        t.getAmount(),
                        t.getCategory().getName(),
                        t.getType(),
                        t.getTransactionDate(),
                        t.getNote()
                ))
                .toList();
    }

    public TransactionResponse updateTransaction(String email, Long id, TransactionRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        if (!transaction.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access");
        }

        Category category = categoryRepository.findByUserAndType(user, request.getType())
                .stream()
                .filter(c -> c.getName().equalsIgnoreCase(request.getCategoryName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Category not found"));

        transaction.setTitle(request.getTitle());
        transaction.setAmount(request.getAmount());
        transaction.setCategory(category);
        transaction.setType(request.getType());
        transaction.setTransactionDate(request.getTransactionDate());
        transaction.setNote(request.getNote());

        Transaction updated = transactionRepository.save(transaction);

        return new TransactionResponse(
                updated.getId(),
                updated.getTitle(),
                updated.getAmount(),
                updated.getCategory().getName(),
                updated.getType(),
                updated.getTransactionDate(),
                updated.getNote()
        );
    }

    public void deleteTransaction(String email, Long id) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        if (!transaction.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access");
        }

        transactionRepository.delete(transaction);
    }

    public List<TransactionResponse> filterByType(String email, String type) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return transactionRepository.findByUserAndType(user, type)
                .stream()
                .map(t -> new TransactionResponse(
                        t.getId(),
                        t.getTitle(),
                        t.getAmount(),
                        t.getCategory().getName(),
                        t.getType(),
                        t.getTransactionDate(),
                        t.getNote()
                ))
                .toList();
    }

    public List<TransactionResponse> filterByCategory(String email, String categoryName) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return transactionRepository.findByUserAndCategory_Name(user, categoryName)
                .stream()
                .map(t -> new TransactionResponse(
                        t.getId(),
                        t.getTitle(),
                        t.getAmount(),
                        t.getCategory().getName(),
                        t.getType(),
                        t.getTransactionDate(),
                        t.getNote()
                ))
                .toList();
    }

    public List<TransactionResponse> filterByDateRange(String email, java.time.LocalDate startDate, java.time.LocalDate endDate) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return transactionRepository.findByUserAndTransactionDateBetween(user, startDate, endDate)
                .stream()
                .map(t -> new TransactionResponse(
                        t.getId(),
                        t.getTitle(),
                        t.getAmount(),
                        t.getCategory().getName(),
                        t.getType(),
                        t.getTransactionDate(),
                        t.getNote()
                ))
                .toList();
    }
}
