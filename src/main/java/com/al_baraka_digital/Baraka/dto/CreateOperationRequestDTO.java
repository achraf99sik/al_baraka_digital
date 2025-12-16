package com.al_baraka_digital.Baraka.dto;

import com.al_baraka_digital.Baraka.enums.OperationType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

public record CreateOperationRequestDTO(
        @NotNull OperationType type,
        @NotNull @Positive BigDecimal amount,
        UUID accountDestinationId
) {}
