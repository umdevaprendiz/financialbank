package com.example.financialbank.dto;

import java.math.BigDecimal;

public record AccountBalanceView(String numberAccount, BigDecimal balance, String status) {}
