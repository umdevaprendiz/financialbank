package com.example.financialbank.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterUserDTO(
    @NotBlank @Size(max = 120) String nome,
    @NotBlank @Size(min = 11, max = 14) String cpf,
    @NotBlank @Email String email,
    @NotBlank @Size(min = 8, max = 72) String senha
) {}
