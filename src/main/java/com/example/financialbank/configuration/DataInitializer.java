package com.example.financialbank.configuration;

import com.example.financialbank.enums.SituationEmail;
import com.example.financialbank.model.User;
import com.example.financialbank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Value("${ADMIN_EMAIL}")
    private String adminEmail;

    @Value("${ADMIN_PASSWORD}")
    private String adminPassword;

    @Value("${ADMIN_CPF}")
    private String adminCpf;

    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.existsByEmail(adminEmail)) {
            if (!userRepository.existsByEmail(adminEmail) && !userRepository.existsByCpf(adminCpf)) {
                User admin = new User();
                admin.setEmail(adminEmail);
                admin.setSenha(passwordEncoder.encode(adminPassword));
                admin.setCpf(adminCpf);
                admin.setRole(Role.ADMIN);
                admin.setNome("Admin");
                admin.setSituationEmail(SituationEmail.VERIFIED);
                userRepository.save(admin);
            }

        }
    }
}