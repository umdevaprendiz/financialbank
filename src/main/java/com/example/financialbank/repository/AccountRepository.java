package com.example.financialbank.repository;

import com.example.financialbank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;



public interface AccountRepository extends JpaRepository<Account, Long> {

}
