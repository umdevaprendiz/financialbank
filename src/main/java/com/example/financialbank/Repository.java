package com.example.financialbank;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Repository extends JpaRepository<User, Integer> {
    boolean existsByCpf(Integer Cpf);
}
