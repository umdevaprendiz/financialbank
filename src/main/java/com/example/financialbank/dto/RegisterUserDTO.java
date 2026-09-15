package com.example.financialbank.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record RegisterUserDTO(
    @NotBlank @Size(max = 120) String nome,
    @NotNull @Past LocalDate dataNascimento,
    @NotBlank @Email String email,
    @NotBlank @Size(min = 8, max = 72) String senha
) {}
