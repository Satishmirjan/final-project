package com.company.disputeresolution.service;

import com.company.disputeresolution.entity.Claim;
import com.company.disputeresolution.entity.Dispute;
import com.company.disputeresolution.entity.User;
import com.company.disputeresolution.entity.enums.DisputeStatus;

public interface IAuditLogService {
    void logStatusChange(Dispute dispute, Claim claim, User changedBy, DisputeStatus oldStatus, DisputeStatus newStatus, String notes);
}
