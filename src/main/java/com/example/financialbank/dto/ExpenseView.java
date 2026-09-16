package com.example.financialbank.dto;

import com.example.financialbank.enums.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ExpenseView(
    Long id,
    BigDecimal amount,
    PaymentMethod paymentMethod,
    String description,
    Boolean worthIt,
    String reason,
    LocalDateTime dateCreation
) {}
