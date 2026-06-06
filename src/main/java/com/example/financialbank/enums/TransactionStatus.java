package com.example.financialbank.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


public enum TransactionStatus {
    PENDENTE("PENDENTE"),
    APROVADA("APROVADA"),
    RECUSADA("RECUSADA"),
    ESTORNADA("ESTORNADA");

    @Getter
    private final String descricao;

    TransactionStatus(String descricao){
        this.descricao = descricao;
    }

}
