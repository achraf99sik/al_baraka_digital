package com.al_baraka_digital.Baraka.repository;

import com.al_baraka_digital.Baraka.enums.OperationStatus;
import com.al_baraka_digital.Baraka.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import java.util.UUID;

@Repository
public interface OperationRepository extends JpaRepository<Operation, UUID> {
    List<Operation> findByAccountSourceIdOrAccountDestinationId(UUID sourceId, UUID destId);
    List<Operation> findByStatus(OperationStatus status);
}
