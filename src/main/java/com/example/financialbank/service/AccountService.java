package com.example.financialbank.service;
import com.example.financialbank.enums.AccountStatus;
import com.example.financialbank.model.Account;
import com.example.financialbank.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository repository;

    public AccountService(AccountRepository repository){
        this.repository = repository;
    }
    public Long countActive(){
        return repository.countByStatus(AccountStatus.ATIVA);
    }

    public Long countBlocked(){
        return repository.countByStatus(AccountStatus.BLOQUEADA);
    }



}
