package com.example.financialbank.service;

import com.example.financialbank.configuration.Role;
import com.example.financialbank.enums.SituationEmail;
import com.example.financialbank.model.User;
import com.example.financialbank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@Primary
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Tentando logar com email: " + email);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    public User saveUser(User user) {

        if(userRepository.findByCpf(user.getCpf()) != null) {
            throw new RuntimeException("CPF já cadastrado.");
        }

        if(userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email já cadastrado.");
        }

        user.setSenha(passwordEncoder.encode(user.getSenha()));

        user.setRole(Role.USER);

        user.setSituationEmail(SituationEmail.PENDING);

        return userRepository.save(user);
    }


}