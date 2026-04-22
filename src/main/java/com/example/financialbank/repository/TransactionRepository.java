package com.example.financialbank.repository;

import com.example.financialbank.model.Account;
import com.example.financialbank.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountOrderByDateDesc(Account account);
}
