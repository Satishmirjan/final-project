package com.company.disputeresolution.entity;

import com.company.disputeresolution.entity.enums.DisputeStatus;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "dispute_audit_logs")
@Data
public class DisputeAuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dispute_id", nullable = false)
    private Dispute dispute;

    @ManyToOne
    @JoinColumn(name = "claim_id", nullable = false)
    private Claim claim;

    @ManyToOne
    @JoinColumn(name = "changed_by_id", nullable = false)
    private User changedBy;

    @Enumerated(EnumType.STRING)
    private DisputeStatus oldStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DisputeStatus newStatus;

    private String notes;

    @Column(nullable = false)
    private LocalDateTime changedAt;
}
