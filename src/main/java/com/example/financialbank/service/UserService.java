package com.example.financialbank.service;

import com.example.financialbank.configuration.Role;
import com.example.financialbank.model.User;
import com.example.financialbank.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Injeção de dependência via construtor
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        User user = userRepository.findByCpf(cpf);
        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        // Criando o objeto User do Spring Security baseado no seu Model
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getCpf())
                .password(user.getSenha())
                .authorities(user.getAuthorities())
                .build();
    }

    public User saveUser(User user) {

        if (user.getNome() == null ||
                user.getSenha() == null ||
                user.getEmail() == null ||
                user.getCpf() == null) {

            throw new RuntimeException("Campos precisam ser preenchidos.");
        }

        user.setRole(Role.USER);

        return userRepository.save(user);
    }


}