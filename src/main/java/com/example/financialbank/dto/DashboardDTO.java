package com.example.financialbank.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

//DTO, oque vai para o front, como uma forma de segurança pra não enviar dados sensíveis.
//saldo da conta
//id
//data da transação
//numero da conta
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardDTO {

    private Long transactionToday;
    private BigDecimal volumeFinanceToday;
    private List<TransactionTypeSummaryDTO> transactionTypeSummary;
    private Long accountActive;
    private Long accountBlocked;

}
