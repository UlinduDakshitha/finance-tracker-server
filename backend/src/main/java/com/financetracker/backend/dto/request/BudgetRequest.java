package com.financetracker.backend.dto.request;
import lombok.Data;

@Data
public class BudgetRequest {
    private String categoryName;
    private Double amount;
    private String period; // MONTHLY
    private Integer month;
    private Integer year;
}
