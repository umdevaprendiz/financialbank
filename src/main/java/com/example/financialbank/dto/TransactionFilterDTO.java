package com.example.financialbank.dto;

import com.example.financialbank.configuration.TransactionType;
import com.example.financialbank.enums.TransactionStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TransactionFilterDTO {
    private TransactionType type;
    private TransactionStatus status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
//Filtro da transações