package com.guinhofsilva.agregadorinvestimentos.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "tb_accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String account_id;

    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    private Instant createdAt;


}
