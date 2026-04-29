package com.company.disputeresolution.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "request_fingerprints")
@Data
public class RequestFingerprint {
    @Id
    private String fingerprint;

    private Long claimId;
    private LocalDateTime createdAt;
}
