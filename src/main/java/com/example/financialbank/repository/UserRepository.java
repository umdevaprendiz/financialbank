package com.example.financialbank.repository;

import com.example.financialbank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//Criação do Repository, buscando no User o parâmetro Long, no caso o Id.
public interface UserRepository extends JpaRepository<User, Long> {
    User findByCpf(String cpf);

    boolean existsByEmail(String email);
}
