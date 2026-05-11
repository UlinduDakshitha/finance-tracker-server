package com.financetracker.backend.repository;
import com.financetracker.backend.entity.Transaction;
import com.financetracker.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);
    List<Transaction> findByUserAndType(User user, String type);
    List<Transaction> findByUserAndTransactionDateBetween(User user, LocalDate startDate, LocalDate endDate);

    List<Transaction> findByUserAndCategory_Name(User user, String categoryName);
}
