package com.financetracker.backend.dto.response;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BudgetResponse {
    private Long id;
    private String categoryName;
    private Double amount;
    private String period;
    private Integer month;
    private Integer year;
    private Double spentAmount;
    private Double remainingAmount;
    private Double progressPercent;
    private String status; // SAFE / EXCEEDED
}
