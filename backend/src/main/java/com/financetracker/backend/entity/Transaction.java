package com.financetracker.backend.entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String type; // INCOME or EXPENSE

    @Column(nullable = false)
    private LocalDate transactionDate;

    private String note;

    private LocalDateTime createdAt;
}
