package com.company.disputeresolution.entity;

import com.company.disputeresolution.entity.enums.ClaimLifecycle;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "claims")
@Data
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @ManyToOne
    @JoinColumn(name = "maker_id")
    private User maker;

    @ManyToOne
    @JoinColumn(name = "checker_id")
    private User checker;

    private String claimType;
    private String claimCategory;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClaimLifecycle lifecycle;

    private BigDecimal totalClaimedAmount;
    private LocalDateTime regulatoryDeadline;

    @Version
    private Long version;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
