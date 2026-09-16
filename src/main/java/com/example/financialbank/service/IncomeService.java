package com.example.financialbank.service;

import com.example.financialbank.dto.UpdateIncomeDTO;
import com.example.financialbank.model.IncomeSetting;
import com.example.financialbank.model.User;
import com.example.financialbank.repository.IncomeSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional(readOnly = true)
public class IncomeService {

    @Autowired
    private IncomeSettingRepository incomeSettingRepository;

    public BigDecimal getIncome(User user) {
        return incomeSettingRepository.findByUser(user)
            .map(IncomeSetting::getAverageIncome)
            .orElse(BigDecimal.ZERO);
    }

    @Transactional
    public BigDecimal updateIncome(User user, UpdateIncomeDTO dto) {
        IncomeSetting setting = incomeSettingRepository.findByUser(user).orElseGet(() -> {
            IncomeSetting s = new IncomeSetting();
            s.setUser(user);
            return s;
        });
        setting.setAverageIncome(dto.averageIncome());
        return incomeSettingRepository.save(setting).getAverageIncome();
    }
}
