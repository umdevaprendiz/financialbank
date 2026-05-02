package com.example.financialbank.controller;

import com.example.financialbank.dto.UserDTO;
import com.example.financialbank.model.Account;
import com.example.financialbank.repository.AccountRepository;
import com.example.financialbank.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank")
public class AccountController {
    private final AccountService service;

    public AccountController(AccountService service){
        this.service = service;
    }
    @PostMapping("/register")
    public ResponseEntity<Account> createAccount(@RequestBody UserDTO userDTO) {
        // Supondo que o UserDTO contenha o ID do usuário ou que você o busque pelo nome
        // Aqui usamos um ID fictício ou vindo do DTO para o exemplo:
        Account newAccount = service.createAccount(userDTO.getId());
        return ResponseEntity.ok(newAccount);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id){
        try {
            service.delete(id);
            return ResponseEntity.ok("Conta encerrada com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable Long id){
        try{
            Account account = service.getAccountById(id);
            return ResponseEntity.ok(account);
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }




}
