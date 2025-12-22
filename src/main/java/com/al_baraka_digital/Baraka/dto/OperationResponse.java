package com.al_baraka_digital.Baraka.dto;

import com.al_baraka_digital.Baraka.enums.OperationStatus;
import com.al_baraka_digital.Baraka.enums.OperationType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class OperationResponse {
    private UUID id;
    private OperationType type;
    private BigDecimal amount;
    private OperationStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime validatedAt;
    private String sourceAccountNumber;
    private String destinationAccountNumber;
}
