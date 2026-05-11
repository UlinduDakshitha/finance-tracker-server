package com.financetracker.backend.dto.response;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TransactionResponse {
    private Long id;
    private String title;
    private Double amount;
    private String categoryName;
    private String type;
    private LocalDate transactionDate;
    private String note;
}