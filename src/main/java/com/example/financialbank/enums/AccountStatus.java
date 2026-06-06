package com.example.financialbank.enums;

public enum AccountStatus {
    ATIVA("ATIVA"),
    BLOQUEADA("BLOQUEADA"),
    PENDENTE("PENDENTE"),
    ENCERRADA("ENCERRADA")
    ;

    private final String descricao;

   AccountStatus(String descricao){
        this.descricao = descricao;
    }
}
