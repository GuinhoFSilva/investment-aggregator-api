package com.guinhofsilva.agregadorinvestimentos.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_accounts")
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "account_id")
    private UUID account_id;

    private String description;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @JsonManagedReference
    private BillingAddress billingAdress;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @CreationTimestamp
    private Instant createdAt;

    @OneToMany(mappedBy = "account")
    private List<AccountStock> accountStocks;

    public Account(){}

    public Account(UUID account_id, String description, BillingAddress billingAdress, User user, Instant createdAt, List<AccountStock> accountStocks) {
        this.account_id = account_id;
        this.description = description;
        this.billingAdress = billingAdress;
        this.user = user;
        this.createdAt = createdAt;
        this.accountStocks = accountStocks;
    }
}
