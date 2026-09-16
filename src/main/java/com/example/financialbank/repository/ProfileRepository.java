package com.example.financialbank.repository;

import com.example.financialbank.model.Profile;
import com.example.financialbank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUser(User user);
    Optional<Profile> findByHandle(String handle);
    boolean existsByHandle(String handle);
}
