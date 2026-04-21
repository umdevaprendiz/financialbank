package com.example.financialbank;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Service{
    private final Repository repository;
    private final SecurityConfig config;

    public Service(Repository repository, SecurityConfig config){
        this.config = config;

        this.repository = repository;
    }

    public User register(User user){
        if(user.setNome();
        user.setCpf();
        user.setEmail();
        user.setSenha(SecurityConfig.passwordEncoder);)

    }

}
