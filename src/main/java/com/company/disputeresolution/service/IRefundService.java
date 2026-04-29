package com.company.disputeresolution.service;

import com.company.disputeresolution.entity.Claim;
import com.company.disputeresolution.entity.Dispute;
import java.math.BigDecimal;

public interface IRefundService {
    void processRefund(Claim claim, Dispute dispute, BigDecimal amount, boolean isProvisional);
    void reverseRefund(Dispute dispute);
}
