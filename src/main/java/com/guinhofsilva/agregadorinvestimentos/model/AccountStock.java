package com.guinhofsilva.agregadorinvestimentos.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_accounts_stocks")
public class AccountStock {
    @EmbeddedId
    private AccountStockId id;

    @ManyToOne
    @MapsId("account_id")
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @MapsId("stock_id")
    @JoinColumn(name = "stock_id")
    private Stock stock;

    private Integer quantity;

    public AccountStock(){}

    public AccountStock(AccountStockId id, Account account, Stock stock, Integer quantity) {
        this.id = id;
        this.account = account;
        this.stock = stock;
        this.quantity = quantity;
    }
}
