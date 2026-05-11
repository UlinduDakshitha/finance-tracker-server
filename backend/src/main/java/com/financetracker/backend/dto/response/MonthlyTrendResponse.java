package com.financetracker.backend.dto.response;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonthlyTrendResponse {
    private String month;
    private Double income;
    private Double expense;
}
