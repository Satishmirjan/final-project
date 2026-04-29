package com.company.disputeresolution.repository;

import com.company.disputeresolution.entity.DisputeReport;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.Optional;

public interface DisputeReportRepository extends JpaRepository<DisputeReport, Long> {
    Optional<DisputeReport> findByReportDate(LocalDate reportDate);
}
