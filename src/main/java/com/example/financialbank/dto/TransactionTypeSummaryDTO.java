package com.example.financialbank.dto;

import com.example.financialbank.configuration.TransactionType;
import com.example.financialbank.model.Account;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class TransactionTypeSummaryDTO {

    private TransactionType type;
    private Long count;

    public TransactionTypeSummaryDTO(TransactionType type, Long count){
        this.count = count;
        this.type = type;
    }
}
//criamos um dto para juntar os atributos tipos e count do
