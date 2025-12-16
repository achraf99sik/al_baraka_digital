package com.al_baraka_digital.Baraka.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountDTO(
        UUID id,
        String accountNumber,
        BigDecimal balance
) {}
