package com.example.financialbank.model;

import com.example.financialbank.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //não pode deixar nulo e tem que ser único para cada usuário.
    @Column(name = "number_account", unique = true, nullable = false)
    private String numberAccount;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    //Don't have permission for change the date creation.
    @CreationTimestamp
    @Column(name = "date_creation", updatable = false)
    private LocalDateTime dateCreation;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AccountStatus status;


    //Muitas contas podem pertencer a muitos usuários.
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)//mapear a coluna da chave estrangeira que é o User.
    private User user;

}
