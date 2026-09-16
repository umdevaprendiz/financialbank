package com.example.financialbank.exception;

// Erro de regra de negócio esperado (ex.: CPF já cadastrado) — diferente de um
// bug inesperado. O GlobalExceptionHandler devolve isso como 400 com a mensagem
// direto pro cliente, ao contrário de uma exceção genérica não mapeada (500, sem detalhe).
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
