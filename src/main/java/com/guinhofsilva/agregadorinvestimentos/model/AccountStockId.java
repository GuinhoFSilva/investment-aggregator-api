package com.guinhofsilva.agregadorinvestimentos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Embeddable
@Getter
@Setter
public class AccountStockId {

    private UUID account_id;

    private String stock_id;

    public AccountStockId(UUID account_id, String stock_id) {
        this.account_id = account_id;
        this.stock_id = stock_id;
    }

    public AccountStockId(){}
}
