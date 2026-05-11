package com.financetracker.backend.service;
import com.financetracker.backend.dto.*;
import com.financetracker.backend.dto.response.CategorySpendingResponse;
import com.financetracker.backend.dto.response.DashboardResponse;
import com.financetracker.backend.dto.response.MonthlyTrendResponse;
import com.financetracker.backend.dto.response.TransactionResponse;
import com.financetracker.backend.entity.Budget;
import com.financetracker.backend.entity.Transaction;
import com.financetracker.backend.entity.User;
import com.financetracker.backend.repository.BudgetRepository;
import com.financetracker.backend.repository.TransactionRepository;
import com.financetracker.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final BudgetRepository budgetRepository;

    public DashboardService(UserRepository userRepository,
                            TransactionRepository transactionRepository,
                            BudgetRepository budgetRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.budgetRepository = budgetRepository;
    }

    public DashboardResponse getDashboard(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Transaction> transactions = transactionRepository.findByUser(user);
        List<Budget> budgets = budgetRepository.findByUser(user);

        double totalIncome = transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("INCOME"))
                .mapToDouble(Transaction::getAmount)
                .sum();

        double totalExpense = transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("EXPENSE"))
                .mapToDouble(Transaction::getAmount)
                .sum();

        double balance = totalIncome - totalExpense;

        // recent 5 transactions
        List<TransactionResponse> recentTransactions = transactions.stream()
                .sorted(Comparator.comparing(Transaction::getTransactionDate).reversed()
                        .thenComparing(Transaction::getId, Comparator.reverseOrder()))
                .limit(5)
                .map(this::mapToResponse)
                .toList();

        // expense by category
        List<CategorySpendingResponse> expenseByCategory = transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("EXPENSE"))
                .collect(Collectors.groupingBy(
                        t -> t.getCategory().getName(),
                        Collectors.summingDouble(Transaction::getAmount)
                ))
                .entrySet()
                .stream()
                .map(e -> new CategorySpendingResponse(e.getKey(), e.getValue()))
                .sorted(Comparator.comparing(CategorySpendingResponse::getAmount).reversed())
                .toList();

        // monthly trend for current year
        int currentYear = LocalDate.now().getYear();
        List<MonthlyTrendResponse> monthlyTrend = java.util.stream.IntStream.rangeClosed(1, 12)
                .mapToObj(month -> {
                    double income = transactions.stream()
                            .filter(t -> t.getTransactionDate().getYear() == currentYear)
                            .filter(t -> t.getTransactionDate().getMonthValue() == month)
                            .filter(t -> t.getType().equalsIgnoreCase("INCOME"))
                            .mapToDouble(Transaction::getAmount)
                            .sum();

                    double expense = transactions.stream()
                            .filter(t -> t.getTransactionDate().getYear() == currentYear)
                            .filter(t -> t.getTransactionDate().getMonthValue() == month)
                            .filter(t -> t.getType().equalsIgnoreCase("EXPENSE"))
                            .mapToDouble(Transaction::getAmount)
                            .sum();

                    return new MonthlyTrendResponse(
                            Month.of(month).name(),
                            income,
                            expense
                    );
                })
                .toList();

        // current month budget usage
        YearMonth currentMonth = YearMonth.now();
        double currentMonthExpense = transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("EXPENSE"))
                .filter(t -> YearMonth.from(t.getTransactionDate()).equals(currentMonth))
                .mapToDouble(Transaction::getAmount)
                .sum();

        double currentMonthBudget = budgets.stream()
                .filter(b -> b.getMonth() == currentMonth.getMonthValue())
                .filter(b -> b.getYear() == currentMonth.getYear())
                .mapToDouble(Budget::getAmount)
                .sum();

        double budgetUsagePercent = currentMonthBudget == 0
                ? 0
                : (currentMonthExpense / currentMonthBudget) * 100;

        return new DashboardResponse(
                totalIncome,
                totalExpense,
                balance,
                budgetUsagePercent,
                currentMonthBudget,
                currentMonthExpense,
                recentTransactions,
                expenseByCategory,
                monthlyTrend
        );
    }

    private TransactionResponse mapToResponse(Transaction t) {
        return new TransactionResponse(
                t.getId(),
                t.getTitle(),
                t.getAmount(),
                t.getCategory().getName(),
                t.getType(),
                t.getTransactionDate(),
                t.getNote()
        );
    }
}
