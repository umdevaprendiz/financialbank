package com.example.financialbank.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

//DTO, oque vai para o front, como uma forma de segurança pra não enviar dados sensíveis.
//saldo da conta
//id
//data da transação
//numero da conta
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {

    private Long id;
    private String numberAccount;
    private BigDecimal balance;
    private LocalDateTime dateCreation;

    AccountDTO(Long id, BigDecimal balance, LocalDateTime dateCreation, String numberAccount){
        this.id = id;
        this.balance = balance;
        this.dateCreation = dateCreation;
        this.numberAccount = numberAccount;
    }
}
