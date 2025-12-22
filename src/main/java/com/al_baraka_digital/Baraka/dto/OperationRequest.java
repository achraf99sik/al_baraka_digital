package com.al_baraka_digital.Baraka.dto;

import com.al_baraka_digital.Baraka.enums.OperationType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OperationRequest {
    private OperationType type;
    private BigDecimal amount;
    private String destinationAccountNumber;
}
