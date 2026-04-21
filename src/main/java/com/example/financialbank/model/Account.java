package com.example.financialbank;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "account")
public class Account {

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //não pode deixar nulo e tem que ser único para cada usuário.
    @Column(name = "number_account", unique = true, nullable = false)
    private String numberAccount;

    @Column(name = "saldo", nullable = false)
    private BigDecimal balance;

    //Don't have permission for change the date creation.
    @CreationTimestamp
    @Column(name = "data_da_criação", updatable = false)
    private LocalDateTime dateCreation;


    //Muitas contas podem pertencer a muitos usuários.
    @ManyToOne
    @JoinColumn(name = "user_account") //mapear a coluna da chave estrangeira que é o User.
    private User usuario;

}
