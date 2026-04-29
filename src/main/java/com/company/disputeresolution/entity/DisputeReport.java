package com.company.disputeresolution.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "dispute_reports")
@Data
public class DisputeReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private LocalDate reportDate;

    private int totalOpen;
    private int totalResolved;
    private double avgResolutionHrs;
    private int slaBreachedCount;
}
