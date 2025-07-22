package com.guinhofsilva.agregadorinvestimentos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "tb_stocks")
@Getter
@Setter
public class Stock {
    @Id
    private String stock_id;

    private String description;

    public Stock(){}

    public Stock(String stock_id, String description) {
        this.stock_id = stock_id;
        this.description = description;
    }
}
