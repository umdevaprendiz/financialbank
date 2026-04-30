package com.example.financialbank.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

//DTO, oque vai para o front, como uma forma de segurança pra não enviar dados sensíveis.
public class AccountDTO {
    private String numberAccount;
    private BigDecimal balance;
    private LocalDateTime dateCreation;
}
