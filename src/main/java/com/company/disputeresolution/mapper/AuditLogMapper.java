package com.company.disputeresolution.mapper;

import com.company.disputeresolution.dto.response.AuditLogDTO;
import com.company.disputeresolution.entity.DisputeAuditLog;

public final class AuditLogMapper {
    private AuditLogMapper() {}

    public static AuditLogDTO toDTO(DisputeAuditLog log) {
        if (log == null) return null;
        return AuditLogDTO.builder()
                .id(log.getId())
                .disputeId(log.getDispute().getId())
                .changedByUserId(log.getChangedBy().getUserId())
                .oldStatus(log.getOldStatus())
                .newStatus(log.getNewStatus())
                .notes(log.getNotes())
                .changedAt(log.getChangedAt())
                .build();
    }
}
