package com.example.financialbank.configuration;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "role")
public class Role {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="name_role", nullable = false, unique = true)
    private String name; //ROLE_USER / ROLE_ADMIN

    @Column(name="description", nullable = false)
    private String description;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "update_at")
    private Date updateAt;
}
