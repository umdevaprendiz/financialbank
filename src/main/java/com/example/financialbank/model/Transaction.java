package com.example.financialbank.model;

import com.example.financialbank.enums.TransactionStatus;
import com.example.financialbank.enums.TransactionType;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tracking_code", unique = true, nullable = false, updatable = false)
    private String trackingCode = UUID.randomUUID().toString();

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type; // PIX, TED, DOC, BOLETO, CARTAO

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status; // PENDENTE, APROVADA, RECUSADA, ESTORNADA

    @ManyToOne
    @JoinColumn(name = "account_origin_id", nullable = false)
    private Account accountOrigin;

    @ManyToOne
    @JoinColumn(name = "account_destination_id", nullable = true)
    private Account accountDestination; // nullable para boletos

    @Column(length = 255)
    private String description;

    @CreationTimestamp
    @Column(name = "date_creation", updatable = false)
    private LocalDateTime dateCreation;

    @UpdateTimestamp
    @Column(name = "date_update")
    private LocalDateTime dateUpdate;
}
