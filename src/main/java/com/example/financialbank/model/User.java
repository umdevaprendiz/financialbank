package com.example.financialbank.model;

import com.example.financialbank.configuration.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cpf_user", unique = true, nullable = false)
    private String cpf;

    @Column(name = "name", nullable = false)
    private String nome;

    @Column(name = "email_user", unique = true, nullable = false)
    private String email;


    //Proíbe o Json de acessar a senha.
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String senha;

    //um usuário pode ter muitas contas.
    @OneToMany(mappedBy = "usuario")
    private List<Account> contas = new ArrayList<>();

    //mapeia a coluna estrangeira, no caso o id.
    @JoinColumn(name = "role_id", nullable = false, referencedColumnName = "id")
    @ManyToOne
    private Role role;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Account> getContas() {
        return contas;
    }

    public void setContas(List<Account> contas) {
        this.contas = contas;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
