package com.company.disputeresolution.service.impl;

import com.company.disputeresolution.entity.Claim;
import com.company.disputeresolution.entity.Dispute;
import com.company.disputeresolution.entity.DisputeAuditLog;
import com.company.disputeresolution.entity.User;
import com.company.disputeresolution.entity.enums.DisputeStatus;
import com.company.disputeresolution.repository.DisputeAuditLogRepository;
import com.company.disputeresolution.service.IAuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements IAuditLogService {
    private final DisputeAuditLogRepository auditLogRepository;

    @Override
    public void logStatusChange(Dispute dispute, Claim claim, User changedBy, DisputeStatus oldStatus, DisputeStatus newStatus, String notes) {
        DisputeAuditLog log = new DisputeAuditLog();
        log.setDispute(dispute);
        log.setClaim(claim);
        log.setChangedBy(changedBy);
        log.setOldStatus(oldStatus);
        log.setNewStatus(newStatus);
        log.setNotes(notes);
        log.setChangedAt(LocalDateTime.now());
        auditLogRepository.save(log);
    }
}
