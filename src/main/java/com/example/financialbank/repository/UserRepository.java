package com.example.financialbank;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByCpf(String Cpf);
    String existsById(Long id);
}
