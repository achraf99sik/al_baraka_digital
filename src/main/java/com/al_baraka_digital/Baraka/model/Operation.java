package com.al_baraka_digital.Baraka.model;

import com.al_baraka_digital.Baraka.enums.OperationStatus;
import com.al_baraka_digital.Baraka.enums.OperationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "operations")
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    private LocalDateTime createdAt;
    private LocalDateTime validatedAt;
    private LocalDateTime executedAt;

    @ManyToOne
    @JoinColumn(name = "account_source_id")
    private Account accountSource;

    @ManyToOne
    @JoinColumn(name = "account_destination_id")
    private Account accountDestination;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
