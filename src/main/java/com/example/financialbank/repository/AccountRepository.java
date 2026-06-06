package com.example.financialbank.repository;

import com.example.financialbank.enums.AccountStatus;
import com.example.financialbank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Long countByStatus(AccountStatus status);

}
