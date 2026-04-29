package com.company.disputeresolution.dto.request;

import com.company.disputeresolution.entity.enums.DisputeStatus;
import lombok.Data;

@Data
public class StatusUpdateRequest {
    private DisputeStatus newStatus;
    private String notes;
    private String changedByUserId;
}
