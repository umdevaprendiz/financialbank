package com.example.financialbank.controller;

import com.example.financialbank.model.Account;
import com.example.financialbank.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bank")
public class AccountController {
    private final AccountService service;

    public AccountController(AccountService service){
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<String> createAccount(@RequestBody Account account){
       service.createAccount();
        return ResponseEntity.ok("Sua conta foi criada!");

    }



}
