package com.example.financialbank.dto;

import com.example.financialbank.configuration.TransactionType;
import com.example.financialbank.model.Account;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;

public class TransactionDTO {

    private BigDecimal value;
    private Account account;

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    public TransactionDTO(BigDecimal value, Account account, TransactionType transactionType){
        this.value = value;
        this.account = account;
        this.transactionType = transactionType;
    }

    public TransactionDTO(){}
}
