package com.financetracker.backend.dto.request;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class BudgetRequest {
    @NotBlank(message = "Category name is required")
    private String categoryName;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private Double amount;

    @NotBlank(message = "Period is required")
    private String period;

    @NotNull(message = "Month is required")
    @Min(1)
    @Max(12)
    private Integer month;

    @NotNull(message = "Year is required")
    private Integer year;
}
