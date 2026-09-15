package com.example.financialbank.service;
import com.example.financialbank.enums.AccountStatus;
import com.example.financialbank.model.Account;
import com.example.financialbank.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Random;

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

    public Account createAccount(Account account){
        String numberAccount;
        do {
            numberAccount = generateNumberAccount();
        } while (repository.existsByNumberAccount(numberAccount));
        account.setNumberAccount(numberAccount);
        return repository.save(account);
    }

    private String generateNumberAccount(){
        Random random = new Random();
        int number = 100000 + random.nextInt(900000); //gera número entre 100000 e 999999
        return String.valueOf(number);
    }

}
