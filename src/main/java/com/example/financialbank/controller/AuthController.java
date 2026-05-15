package com.example.financialbank.controller;


import com.example.financialbank.model.User;
import com.example.financialbank.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String Cadastro(Model model) {
        model.addAttribute("user", new User());
        return "cadastro";
    }

    //método perfeito de cadastro pra mim.
    @PostMapping("/cadastro")
    public String createUser(User user) {
        userService.saveUser(user);
        return "redirect:/login";
    }


    @GetMapping("/login")
    public String exibirLogin() {
        return "login";
    }
}