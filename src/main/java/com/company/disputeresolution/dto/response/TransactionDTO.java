package com.company.disputeresolution.dto.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class TransactionDTO {
    private String transactionId;
    private String merchantName;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private String status;
}
