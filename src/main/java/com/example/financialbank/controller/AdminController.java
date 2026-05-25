package com.example.financialbank.controller;


import com.example.financialbank.repository.UserRepository;
import com.example.financialbank.service.AdminService;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
public class AdminController {
    private final AdminService service;
    private final UserRepository repository;

    public AdminController(AdminService service, UserRepository repository){
        this.repository = repository;
        this.service = service;
    }


}
