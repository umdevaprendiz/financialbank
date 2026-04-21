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

    @OneToOne
    @Column(name = "number_account", unique = true)
    private String numberAccount;

    @Column(name = "saldo")
    private BigDecimal balance;

    @CreationTimestamp
    @Column(name = "data_da_criação")
    private LocalDateTime dateCreation;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private User usuario;

}
