package com.example.financialbank.dto;

import com.example.financialbank.enums.PaymentMethod;

import java.math.BigDecimal;

public record SpendingByMethodView(PaymentMethod method, BigDecimal total, long count) {}
