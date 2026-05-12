package com.financetracker.backend.service;

import com.financetracker.backend.dto.request.BudgetRequest;
import com.financetracker.backend.dto.response.BudgetResponse;
import com.financetracker.backend.entity.Budget;
import com.financetracker.backend.entity.Category;
import com.financetracker.backend.entity.Transaction;
import com.financetracker.backend.entity.User;
import com.financetracker.backend.repository.BudgetRepository;
import com.financetracker.backend.repository.CategoryRepository;
import com.financetracker.backend.repository.TransactionRepository;
import com.financetracker.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;

    public BudgetService(BudgetRepository budgetRepository,
                         UserRepository userRepository,
                         CategoryRepository categoryRepository,
                         TransactionRepository transactionRepository) {
        this.budgetRepository = budgetRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.transactionRepository = transactionRepository;
    }

    public BudgetResponse createBudget(String email, BudgetRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Category category = resolveExpenseCategory(user, request.getCategoryId());

        Budget budget = Budget.builder()
                .user(user)
                .category(category)
                .amount(request.getAmount())
                .period(request.getPeriod())
                .month(request.getMonth())
                .year(request.getYear())
                .createdAt(LocalDateTime.now())
                .build();

        Budget saved = budgetRepository.save(budget);
        return buildBudgetResponse(saved);
    }

    public List<BudgetResponse> getAllBudgets(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return budgetRepository.findByUser(user)
                .stream()
                .map(this::buildBudgetResponse)
                .toList();
    }

    public BudgetResponse updateBudget(String email, Long id, BudgetRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Budget budget = budgetRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Budget not found"));

        Category category = resolveExpenseCategory(user, request.getCategoryId());

        budget.setCategory(category);
        budget.setAmount(request.getAmount());
        budget.setPeriod(request.getPeriod());
        budget.setMonth(request.getMonth());
        budget.setYear(request.getYear());

        Budget updated = budgetRepository.save(budget);
        return buildBudgetResponse(updated);
    }

    public void deleteBudget(String email, Long id) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Budget budget = budgetRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Budget not found"));

        budgetRepository.delete(budget);
    }

    private Category resolveExpenseCategory(User user, Long categoryId) {
        if (categoryId == null) {
            throw new RuntimeException("Category ID is required");
        }

        Category category = categoryRepository.findByIdAndUser(categoryId, user)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (!"EXPENSE".equalsIgnoreCase(category.getType())) {
            throw new RuntimeException("Selected category must be EXPENSE");
        }

        return category;
    }

    private BudgetResponse buildBudgetResponse(Budget budget) {
        User user = budget.getUser();
        String categoryName = budget.getCategory().getName();

        List<Transaction> transactions = transactionRepository.findByUser(user)
                .stream()
                .filter(t -> t.getType().equalsIgnoreCase("EXPENSE"))
                .filter(t -> t.getCategory().getName().equalsIgnoreCase(categoryName))
                .filter(t -> t.getTransactionDate().getMonthValue() == budget.getMonth())
                .filter(t -> t.getTransactionDate().getYear() == budget.getYear())
                .toList();

        double spentAmount = transactions.stream()
                .mapToDouble(Transaction::getAmount)
                .sum();

        double remainingAmount = budget.getAmount() - spentAmount;
        double progressPercent = budget.getAmount() == 0 ? 0 : (spentAmount / budget.getAmount()) * 100;
        String status = spentAmount > budget.getAmount() ? "EXCEEDED" : "SAFE";

        return new BudgetResponse(
                budget.getId(),
                categoryName,
                budget.getAmount(),
                budget.getPeriod(),
                budget.getMonth(),
                budget.getYear(),
                spentAmount,
                remainingAmount,
                progressPercent,
                status
        );
    }
}