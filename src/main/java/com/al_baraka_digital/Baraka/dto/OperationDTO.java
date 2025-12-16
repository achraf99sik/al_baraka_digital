package com.al_baraka_digital.Baraka.dto;

import com.al_baraka_digital.Baraka.enums.OperationStatus;
import com.al_baraka_digital.Baraka.enums.OperationType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record OperationDTO(
        UUID id,
        OperationType type,
        BigDecimal amount,
        OperationStatus status,
        LocalDateTime createdAt,
        LocalDateTime validatedAt,
        LocalDateTime executedAt,
        UUID accountSourceId,
        UUID accountDestinationId
) {}
