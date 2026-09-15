package com.example.financialbank.dto;


import lombok.*;

//Oque pode ser mudado no DTO.
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserUpdateDTO {
    private String nome;
    private String email;
    private String senha;
    private String cpf;
}
