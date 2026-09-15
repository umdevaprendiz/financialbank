package com.example.financialbank.controller;

import com.example.financialbank.model.User;
import com.example.financialbank.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registerPage(Model model) {

        model.addAttribute("user", new User());

        return "cadastropage/cadastro";
    }

    @PostMapping("/register")
    public String createUser(@ModelAttribute User user, Model model) {
        System.out.println("ENTROU NO POST");

        // 1. IMPORTANTE: Exiba no console para ver se os dados do formulário estão chegando preenchidos
        System.out.println("Dados do formulário: " + user.getNome() + " - " + user.getEmail());

        // 2. CHAME O SEU SERVIÇO PARA SALVAR NO BANCO
        User usuarioSalvo = userService.saveUser(user); // Substitua 'salvar' pelo nome do seu método no UserService

        // 3. Se você quiser enviar o usuário salvo de volta para a página principal ou de sucesso:
        model.addAttribute("usuarioCadastrado", usuarioSalvo);

        return "redirect:/"; // Redireciona para a home
    }

    @GetMapping("/")
    public String homePage() {

        return "home";
    }
}