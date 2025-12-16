package com.al_baraka_digital.Baraka.dto;
import com.al_baraka_digital.Baraka.enums.Role;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserDTO(
        UUID id,
        String email,
        String fullName,
        Role role,
        boolean active,
        LocalDateTime createdAt
) {}
