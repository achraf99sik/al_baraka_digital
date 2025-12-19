package com.al_baraka_digital.Baraka.repository;

import com.al_baraka_digital.Baraka.model.Document;
import com.al_baraka_digital.Baraka.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import java.util.UUID;

@Repository
public interface DocumentRepository extends JpaRepository<Document, UUID> {
    List<Document> findByOperation(Operation operation);
}
