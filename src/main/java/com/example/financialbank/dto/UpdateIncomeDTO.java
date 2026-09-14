package com.example.financialbank.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record UpdateIncomeDTO(
    @NotNull @PositiveOrZero BigDecimal averageIncome
) {}
