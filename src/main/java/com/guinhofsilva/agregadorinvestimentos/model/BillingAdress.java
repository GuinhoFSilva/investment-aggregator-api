package com.guinhofsilva.agregadorinvestimentos.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "tb_billing_adress")
@Getter
@Setter
public class BillingAdress {
    @Id
    @Column(name = "account_id")
    private UUID account_id;

    @OneToOne
    @JoinColumn(name = "account_id")
    @MapsId
    private Account account;

    private String street;

    private Integer number;

    public BillingAdress(){}

    public BillingAdress(UUID account_id, String street, Integer number) {
        this.account_id = account_id;
        this.street = street;
        this.number = number;
    }
}
