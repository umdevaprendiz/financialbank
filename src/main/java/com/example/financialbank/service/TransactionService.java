package com.example.financialbank.service;


import com.example.financialbank.Specification.TransactionSpecification;
import com.example.financialbank.dto.TransactionFilterDTO;
import com.example.financialbank.dto.TransactionTypeSummaryDTO;
import com.example.financialbank.enums.TransactionStatus;
import com.example.financialbank.enums.TransactionType;
import com.example.financialbank.model.Transaction;
import com.example.financialbank.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;



//dados pro dashboard, listagem de transações, detalhes de uma transição.
//cria uma transação, atualizar o saldo das contas envolvidas, mudar os status.
//erros: saldo insuficiente, conta bloqueada, conta destino inexistente

@Service
public class TransactionService {
    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository){
        this.repository = repository;
    }
    //buscar todas as transações feitas.
    public List<Transaction> findAll() {
        return repository.findAll();
    }

    public Long countToday() {
        return repository.countToday();
    }

    public BigDecimal sumAmountToday() {
        return repository.sumAmountToday();
    }

    public List<TransactionTypeSummaryDTO> countGroupByType(){
        return repository.countGroupByType();
    }

    public List<Transaction> findByFilter(TransactionFilterDTO filter){
        return repository.findAll(TransactionSpecification.filterBy(filter));
    }
}
