package com.example.financialbank;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public String getNumberAccount() {
        return numberAccount;
    }

    public void setNumberAccount(String numberAccount) {
        this.numberAccount = numberAccount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }


    //não pode deixar nulo e tem que ser único para cada usuário.
    @Column(name = "number_account", unique = true, nullable = false)
    private String numberAccount;

    @Column(name = "saldo")
    private BigDecimal balance;

    //Don't have permission for change the date creation.
    @CreationTimestamp
    @Column(name = "data_da_criação", updatable = false)
    private LocalDateTime dateCreation;


    //Muitas contas podem pertencer a muitos usuários.
    @ManyToOne
    @JoinColumn(name = "user_account") //
    private User usuario;

}
