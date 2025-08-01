package com.guinhofsilva.agregadorinvestimentos.repository;

import com.guinhofsilva.agregadorinvestimentos.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    @Query
    Optional<User> findByUsername(String username);

    @Query
    Optional<User> findByEmail(String email);
}
