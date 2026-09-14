package com.example.financialbank.controller;

import com.example.financialbank.dto.RegisterUserDTO;
import com.example.financialbank.model.User;
import com.example.financialbank.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@Valid @RequestBody RegisterUserDTO dto) {
        User usuarioSalvo = userService.registerUser(dto);
        return ResponseEntity.status(201).body(usuarioSalvo);
    }
}
