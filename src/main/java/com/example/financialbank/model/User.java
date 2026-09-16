package com.example.financialbank.model;

import com.example.financialbank.enums.SituationEmail;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

import com.example.financialbank.configuration.Role;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "name", nullable = false)
    private String nome;

    private boolean ativo = true;

    private LocalDateTime atualizadoEm;

    @Column(name = "email_user", unique = true, nullable = false)
    private String email;

    @Column(name="situationEmail", unique = false, nullable = false)
    @Enumerated(EnumType.STRING)
    private SituationEmail situationEmail;

    @Enumerated(EnumType.STRING)
    private Role role;


    //Proíbe o Json de acessar a senha.
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String senha;

    //um usuário pode ter muitas contas.
    @OneToMany(mappedBy = "user")
    private Set<Account> contas;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    // getPassword() vem da interface UserDetails (Spring Security usa isso internamente,
    // fora do Jackson) — sem @JsonIgnore aqui, o hash da senha vazava em QUALQUER endpoint
    // que devolvesse um User (cadastro, listagem de usuários no admin, buscar por id).
    @JsonIgnore
    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.ativo;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return this.ativo;
    }
}
