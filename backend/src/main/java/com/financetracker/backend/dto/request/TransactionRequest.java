package com.financetracker.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.time.LocalDate;

@Data
public class TransactionRequest {
    @NotBlank(message = "Title is required")
    private String title;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private Double amount;

    @NotBlank(message = "Category name is required")
    private String categoryName;

    @NotBlank(message = "Type is required")
    private String type;

    @NotNull(message = "Transaction date is required")
    private LocalDate transactionDate;

    private String note;
}