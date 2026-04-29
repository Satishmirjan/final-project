package com.company.disputeresolution.dto.response;

import com.company.disputeresolution.entity.enums.ClaimLifecycle;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ClaimDTO {
    private Long id;
    private String customerId;
    private String makerId;
    private String checkerId;
    private ClaimLifecycle lifecycle;
    private BigDecimal totalClaimedAmount;
    private LocalDateTime regulatoryDeadline;
    private LocalDateTime createdAt;
}
