package com.financetracker.backend.dto.request;
import lombok.Data;

@Data
public class CategoryRequest {
    private String name;
    private String type; // INCOME or EXPENSE
}
