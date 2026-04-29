package com.company.disputeresolution.dto.response;

import com.company.disputeresolution.entity.enums.DisputePhase;
import com.company.disputeresolution.entity.enums.DisputeStatus;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class DisputeDTO {
    private Long id;
    private Long claimId;
    private String customerId;
    private String transactionId;
    private String makerId;
    private String checkerId;
    private BigDecimal amount;
    private DisputeStatus status;
    private DisputePhase phase;
    private String reason;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
