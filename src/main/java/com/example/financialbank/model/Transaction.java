package com.example.financialbank.model;

import com.example.financialbank.configuration.TransactionType;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "value_transaction", updatable = false)
    private BigDecimal value;


    @Column(name  = "date_transaction", updatable = false)
    @CreationTimestamp //diz o horário exato que foi criado.
    private LocalDateTime date;


    //como criamos o enum, podemos utiliza-lo como atributo do entity.
    @Column(name = "transaction_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;


    @Column(name = "current_balance", updatable = false)
    private BigDecimal currentBalance;

    @JoinColumn(name = "account_id", nullable = false)
    @ManyToOne //muitas transições podem ter em uma conta.
    private Account account;

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
