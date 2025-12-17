package com.al_baraka_digital.Baraka.repository;

import com.al_baraka_digital.Baraka.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
