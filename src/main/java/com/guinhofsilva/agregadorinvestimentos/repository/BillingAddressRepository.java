package com.guinhofsilva.agregadorinvestimentos.repository;

import com.guinhofsilva.agregadorinvestimentos.model.BillingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BillingAddressRepository extends JpaRepository<BillingAddress, UUID> {
}
