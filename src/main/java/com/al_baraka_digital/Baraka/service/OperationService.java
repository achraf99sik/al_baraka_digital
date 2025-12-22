package com.al_baraka_digital.Baraka.service;

import com.al_baraka_digital.Baraka.enums.OperationType;
import com.al_baraka_digital.Baraka.model.Operation;
import com.al_baraka_digital.Baraka.model.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface OperationService {
    Operation createOperation(User user, OperationType type, BigDecimal amount, String destinationAccountNumber);
    void executeOperation(Operation operation);
    Operation approveOperation(UUID operationId);
    Operation rejectOperation(UUID operationId);
    List<Operation> getClientOperations(User user);
    List<Operation> getPendingOperations();
    Operation getOperationById(UUID id);
}
