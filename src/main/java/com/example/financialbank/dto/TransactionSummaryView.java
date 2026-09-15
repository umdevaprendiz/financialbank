package com.example.financialbank.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionSummaryView(
    String trackingCode,
    String type,
    BigDecimal amount,
    String direction,
    String description,
    LocalDateTime dateCreation
) {}
