package com.example.financialbank.dto;

import com.example.financialbank.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreateExpenseDTO(
    @NotNull @Positive BigDecimal amount,
    @NotNull PaymentMethod paymentMethod,
    @Size(max = 200) String description,
    Boolean worthIt,
    @Size(max = 300) String reason
) {}
