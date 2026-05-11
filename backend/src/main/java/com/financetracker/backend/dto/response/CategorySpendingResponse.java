package com.financetracker.backend.dto.response;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategorySpendingResponse {
    private String categoryName;
    private Double amount;
}
