package com.financetracker.backend.dto.request;

import lombok.Data;
import java.time.LocalDate;

@Data
public class TransactionRequest {
    private String title;
    private Double amount;
    private String categoryName;
    private String type; // INCOME or EXPENSE
    private LocalDate transactionDate;
    private String note;
}