package com.al_baraka_digital.Baraka.service.impl;

import com.al_baraka_digital.Baraka.enums.OperationStatus;
import com.al_baraka_digital.Baraka.enums.OperationType;
import com.al_baraka_digital.Baraka.model.Account;
import com.al_baraka_digital.Baraka.model.Operation;
import com.al_baraka_digital.Baraka.model.User;
import com.al_baraka_digital.Baraka.repository.OperationRepository;
import com.al_baraka_digital.Baraka.service.AccountService;
import com.al_baraka_digital.Baraka.service.DocumentService;
import com.al_baraka_digital.Baraka.service.OperationService;
import lombok.RequiredArgsConstructor;
import com.al_baraka_digital.Baraka.exception.BusinessValidationException;
import com.al_baraka_digital.Baraka.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {

    private final OperationRepository operationRepository;
    private final AccountService accountService;
    private final DocumentService documentService;

    private static final BigDecimal AUTO_VALIDATION_LIMIT = new BigDecimal("10000");

    @Override
    @Transactional
    public Operation createOperation(User user, OperationType type, BigDecimal amount, String destinationAccountNumber) {
        Account sourceAccount = accountService.getAccountByUser(user);
        Account destinationAccount = null;

        if (type == OperationType.TRANSFER) {
            destinationAccount = accountService.getAccountByNumber(destinationAccountNumber);
            if (sourceAccount.getId().equals(destinationAccount.getId())) {
                throw new BusinessValidationException("Cannot transfer to the same account");
            }
        }

        // Check sufficient balance for withdrawals and transfers
        if (type == OperationType.WITHDRAW || type == OperationType.TRANSFER) {
            if (sourceAccount.getBalance().compareTo(amount) < 0) {
                throw new BusinessValidationException("Insufficient balance");
            }
        }

        Operation operation = Operation.builder()
                .type(type)
                .amount(amount)
                .accountSource(sourceAccount)
                .accountDestination(destinationAccount)
                .status(OperationStatus.PENDING) 
                .build();

        // Auto-validation logic
        if (amount.compareTo(AUTO_VALIDATION_LIMIT) <= 0) {
            executeOperation(operation);
        }

        return operationRepository.save(operation);
    }

    @Override
    @Transactional
    public void executeOperation(Operation operation) {
        Account source = operation.getAccountSource();
        Account dest = operation.getAccountDestination();
        BigDecimal amount = operation.getAmount();

        switch (operation.getType()) {
            case DEPOSIT:
                accountService.updateBalance(source, amount);
                break;
            case WITHDRAW:
                if (source.getBalance().compareTo(amount) < 0) {
                    throw new BusinessValidationException("Insufficient balance during execution");
                }
                accountService.updateBalance(source, amount.negate());
                break;
            case TRANSFER:
                 if (source.getBalance().compareTo(amount) < 0) {
                    throw new BusinessValidationException("Insufficient balance during execution");
                }
                accountService.updateBalance(source, amount.negate());
                if (dest != null) {
                    accountService.updateBalance(dest, amount);
                }
                break;
        }

        operation.setStatus(OperationStatus.APPROVED);
        operation.setValidatedAt(LocalDateTime.now());
        operation.setExecutedAt(LocalDateTime.now());
    }

    @Override
    @Transactional
    public Operation approveOperation(UUID operationId) {
        Operation operation = operationRepository.findById(operationId)
                .orElseThrow(() -> new ResourceNotFoundException("Operation not found"));

        if (operation.getStatus() != OperationStatus.PENDING) {
            throw new BusinessValidationException("Operation is not PENDING");
        }

        // If high amount, require documents
        if (operation.getAmount().compareTo(AUTO_VALIDATION_LIMIT) > 0) {
             if (documentService.getDocumentsByOperation(operation).isEmpty()) {
                 throw new BusinessValidationException("Cannot approve high amount operation without supporting documents");
             }
        }

        executeOperation(operation);
        return operationRepository.save(operation);
    }

    @Override
    @Transactional
    public Operation rejectOperation(UUID operationId) {
        Operation operation = operationRepository.findById(operationId)
                .orElseThrow(() -> new ResourceNotFoundException("Operation not found"));

        if (operation.getStatus() != OperationStatus.PENDING) {
            throw new BusinessValidationException("Operation is not PENDING");
        }

        operation.setStatus(OperationStatus.REJECTED);
        operation.setValidatedAt(LocalDateTime.now());
        return operationRepository.save(operation);
    }

    @Override
    public List<Operation> getClientOperations(User user) {
        Account account = accountService.getAccountByUser(user);
        return operationRepository.findByAccountSourceIdOrAccountDestinationId(account.getId(), account.getId());
    }

    @Override
    public List<Operation> getPendingOperations() {
        return operationRepository.findByStatus(OperationStatus.PENDING);
    }
    
    @Override
    public Operation getOperationById(UUID id) {
        return operationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Operation not found"));
    }
}
