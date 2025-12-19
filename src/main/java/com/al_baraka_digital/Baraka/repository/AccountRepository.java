package com.al_baraka_digital.Baraka.repository;

import com.al_baraka_digital.Baraka.model.Account;
import com.al_baraka_digital.Baraka.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    Optional<Account> findByAccountNumber(String accountNumber);
    Optional<Account> findByOwner(User owner);
}
