package com.company.disputeresolution.repository;

import com.company.disputeresolution.entity.Dispute;
import com.company.disputeresolution.entity.DisputeAuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DisputeAuditLogRepository extends JpaRepository<DisputeAuditLog, Long> {
    List<DisputeAuditLog> findByDisputeOrderByChangedAtDesc(Dispute dispute);
}
