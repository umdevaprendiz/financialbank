package com.example.financialbank.repository;

import com.example.financialbank.model.Account;
import com.example.financialbank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

//Criação do Repository, buscando no User o parâmetro Long, no caso o Id.
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM user WHERE cpf = ?1", nativeQuery = true)
    User findByCpf(String Cpf);
}
