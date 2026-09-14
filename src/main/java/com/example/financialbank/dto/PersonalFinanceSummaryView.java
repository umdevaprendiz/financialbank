package com.example.financialbank.dto;

import java.math.BigDecimal;
import java.util.List;

public record PersonalFinanceSummaryView(
    BigDecimal averageIncome,
    BigDecimal totalSpentThisMonth,
    BigDecimal remaining,
    BigDecimal percentOfIncomeSpent,
    List<SpendingByMethodView> byMethod,
    BigDecimal previousMonthTotal,
    BigDecimal percentChangeVsPreviousMonth
) {}
