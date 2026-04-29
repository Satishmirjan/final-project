package com.company.disputeresolution.dto.request;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class DisputeRequest {
    private String transactionId;
    private String customerId;
    private String reason;
    private String intakeReasonCode;
    private BigDecimal amount;
}
