package com.example.financialbank.repository;

import com.example.financialbank.model.ExpenseEntry;
import com.example.financialbank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ExpenseEntryRepository extends JpaRepository<ExpenseEntry, Long> {
    List<ExpenseEntry> findByUserOrderByDateCreationDesc(User user);
    List<ExpenseEntry> findByUserAndDateCreationBetween(User user, LocalDateTime start, LocalDateTime end);
    List<ExpenseEntry> findByUserAndWorthItFalseOrderByDateCreationDesc(User user);
}
