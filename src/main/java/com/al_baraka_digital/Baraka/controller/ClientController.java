package com.al_baraka_digital.Baraka.controller;

import com.al_baraka_digital.Baraka.dto.OperationRequest;
import com.al_baraka_digital.Baraka.dto.OperationResponse;
import com.al_baraka_digital.Baraka.model.Operation;
import com.al_baraka_digital.Baraka.model.User;
import com.al_baraka_digital.Baraka.mapper.OperationMapper;
import com.al_baraka_digital.Baraka.service.DocumentService;
import com.al_baraka_digital.Baraka.service.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/client/operations")
@RequiredArgsConstructor
public class ClientController {

    private final OperationService operationService;
    private final DocumentService documentService;
    private final OperationMapper operationMapper;

    @PostMapping
    public ResponseEntity<OperationResponse> createOperation(
            @AuthenticationPrincipal User user,
            @RequestBody OperationRequest request) {
        
        Operation operation = operationService.createOperation(
                user,
                request.getType(),
                request.getAmount(),
                request.getDestinationAccountNumber()
        );
        return ResponseEntity.ok(operationMapper.toResponse(operation));
    }

    @PostMapping("/{id}/document")
    public ResponseEntity<?> uploadDocument(
            @PathVariable UUID id,
            @RequestParam("file") MultipartFile file) {
        
        Operation operation = operationService.getOperationById(id);
        documentService.uploadDocument(operation, file);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<OperationResponse>> listOperations(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(operationService.getClientOperations(user).stream()
                .map(operationMapper::toResponse)
                .collect(Collectors.toList()));
    }
}
