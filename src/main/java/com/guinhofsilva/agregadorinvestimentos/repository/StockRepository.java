package com.guinhofsilva.agregadorinvestimentos.repository;

import com.guinhofsilva.agregadorinvestimentos.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {
}
