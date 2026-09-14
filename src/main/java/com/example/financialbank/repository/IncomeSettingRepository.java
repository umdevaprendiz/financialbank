package com.example.financialbank.repository;

import com.example.financialbank.model.IncomeSetting;
import com.example.financialbank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IncomeSettingRepository extends JpaRepository<IncomeSetting, Long> {
    Optional<IncomeSetting> findByUser(User user);
}
