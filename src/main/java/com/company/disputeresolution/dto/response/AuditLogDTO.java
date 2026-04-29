package com.company.disputeresolution.dto.response;

import com.company.disputeresolution.entity.enums.DisputeStatus;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class AuditLogDTO {
    private Long id;
    private Long disputeId;
    private String changedByUserId;
    private DisputeStatus oldStatus;
    private DisputeStatus newStatus;
    private String notes;
    private LocalDateTime changedAt;
}
