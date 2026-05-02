package com.example.financialbank.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

//DTO, oque vai para o front, como uma forma de segurança pra não enviar dados sensíveis.
//saldo da conta
//id
//data da transação
//numero da conta
public class AccountDTO {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Long id;
    private String numberAccount;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    private BigDecimal balance;
    private LocalDateTime dateCreation;

    AccountDTO(Long id, BigDecimal balance, LocalDateTime dateCreation, String numberAccount){
        this.id = id;
        this.balance = balance;
        this.dateCreation = dateCreation;
        this.numberAccount = numberAccount;
    }
}
