package com.guinhofsilva.agregadorinvestimentos.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "tb_billing_address")
@Getter
@Setter
public class BillingAddress {
    @Id
    @Column(name = "account_id")
    private UUID account_id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    @JsonBackReference
    @MapsId
    private Account account;

    private String street;

    private Integer number;

    public BillingAddress(){}

    public BillingAddress(Account account, String street, Integer number) {
        this.account = account;
        this.street = street;
        this.number = number;
    }
}
