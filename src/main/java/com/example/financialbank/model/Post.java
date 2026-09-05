package com.example.financialbank.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name="author_id", nullable = false)
    private Profile author;

    @Column(length = 500)
    private String caption;

    @Column(nullable = false)
    private String urlImage;

    @CreationTimestamp
    @Column(name="date_creation", updatable = false)
    private LocalDateTime dateCreation;
}
