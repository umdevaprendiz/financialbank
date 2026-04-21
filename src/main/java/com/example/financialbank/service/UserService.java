package com.example.financialbank.service;

import com.example.financialbank.repository.UserRepository;
import com.example.financialbank.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
    }

    //Registrar User.
    public User register(User user) {
        if (user.getNome() == null ||
                user.getCpf() == null ||
                user.getEmail() == null ||
                user.getSenha() == null) {
            throw new RuntimeException("Campos obrigatórios não preenchidos");
        }
        //Conferir se exister um User pelo Cpf, visto que o Cpf é unico.
        if (repository.existsByCpf(user.getCpf())) {
            throw new RuntimeException("User já cadastrado!");
        }

        //criptografa a senha...
        String senhaCriptografada = passwordEncoder.encode(user.getSenha());
        user.setSenha(senhaCriptografada);
        return repository.save(user);
    }

}


