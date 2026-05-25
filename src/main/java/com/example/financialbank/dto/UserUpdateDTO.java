package com.example.financialbank.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Oque pode ser mudado no DTO.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {
    private String nome;
    private String email;
    private String senha;
    private String cpf;
}
