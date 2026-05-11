package com.financetracker.backend.dto.response;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DashboardResponse {
    private Double totalIncome;
    private Double totalExpense;
    private Double balance;
    private Double budgetUsagePercent;
    private Double currentMonthBudget;
    private Double currentMonthExpense;
    private List<TransactionResponse> recentTransactions;
    private List<CategorySpendingResponse> expenseByCategory;
    private List<MonthlyTrendResponse> monthlyTrend;
}
