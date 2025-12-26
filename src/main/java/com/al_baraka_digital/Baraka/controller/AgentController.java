package com.al_baraka_digital.Baraka.controller;

import com.al_baraka_digital.Baraka.dto.OperationResponse;
import com.al_baraka_digital.Baraka.mapper.OperationMapper;
import com.al_baraka_digital.Baraka.service.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/agent/operations")
@RequiredArgsConstructor
public class AgentController {

    private final OperationService operationService;
    private final OperationMapper operationMapper;

    @GetMapping("/pending")
    public ResponseEntity<List<OperationResponse>> listPendingOperations() {
        return ResponseEntity.ok(operationService.getPendingOperations().stream()
                .map(operationMapper::toResponse)
                .collect(Collectors.toList()));
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<OperationResponse> approveOperation(@PathVariable UUID id) {
        return ResponseEntity.ok(operationMapper.toResponse(operationService.approveOperation(id)));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<OperationResponse> rejectOperation(@PathVariable UUID id) {
        return ResponseEntity.ok(operationMapper.toResponse(operationService.rejectOperation(id)));
    }
}
