package com.example.financialbank.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    @Column(nullable = false, unique = true)
    private String handle;

    @Column(length = 200)
    private String bio;

    private String avatarUrl;

    @CreationTimestamp
    @Column(name = "date_creation", nullable = false)
    private LocalDateTime dateCreation;
}

//User é a identidade bancária, agora estamos falando do perfil público da rede social.
