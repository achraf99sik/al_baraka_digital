package com.al_baraka_digital.Baraka.model;

import com.al_baraka_digital.Baraka.enums.OperationStatus;
import com.al_baraka_digital.Baraka.enums.OperationType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "operations")
public class Operation {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "UUID")
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OperationType type;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OperationStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "validated_at")
    private LocalDateTime validatedAt;

    @Column(name = "executed_at")
    private LocalDateTime executedAt;

    @ManyToOne
    @JoinColumn(name = "account_source_id", nullable = false)
    private Account accountSource;

    @ManyToOne
    @JoinColumn(name = "account_destination_id")
    private Account accountDestination;

    @OneToOne(mappedBy = "operation", cascade = CascadeType.ALL)
    private Document document;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
