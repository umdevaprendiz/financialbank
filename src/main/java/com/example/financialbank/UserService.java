package com.example.financialbank;

import org.springframework.security.crypto.password.PasswordEncoder;

public class UserService {
    private final Repository repository;
    private final PasswordEncoder passwordEncoder;
    private final User user;

    public UserService(Repository repository, PasswordEncoder passwordEncoder, User user) {
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
        this.user = user;
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
            throw new RuntimeException("Cpf já cadastrado!");
        }

        String senhaCriptografada = passwordEncoder.encode(user.getSenha());
        user.setSenha(senhaCriptografada);
        return repository.save(user);
    }

    public User Account(Account  account){

    }
}


