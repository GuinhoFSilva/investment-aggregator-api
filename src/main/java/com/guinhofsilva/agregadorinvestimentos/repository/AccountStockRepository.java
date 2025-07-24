package com.guinhofsilva.agregadorinvestimentos.repository;

import com.guinhofsilva.agregadorinvestimentos.model.AccountStock;
import com.guinhofsilva.agregadorinvestimentos.model.AccountStockId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountStockRepository extends JpaRepository<AccountStock, AccountStockId> {
}
