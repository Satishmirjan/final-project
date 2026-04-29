package com.company.disputeresolution.service.impl;

import com.company.disputeresolution.entity.Claim;
import com.company.disputeresolution.entity.Dispute;
import com.company.disputeresolution.entity.Refund;
import com.company.disputeresolution.entity.enums.RefundStatus;
import com.company.disputeresolution.repository.RefundRepository;
import com.company.disputeresolution.service.IRefundService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RefundServiceImpl implements IRefundService {
    private final RefundRepository refundRepository;

    @Override
    @Transactional
    public void processRefund(Claim claim, Dispute dispute, BigDecimal amount, boolean isProvisional) {
        Refund refund = new Refund();
        refund.setClaim(claim);
        refund.setDispute(dispute);
        refund.setAmount(amount);
        refund.setStatus(isProvisional ? RefundStatus.PROVISIONAL : RefundStatus.FINAL);
        refund.setProvisional(isProvisional);
        refund.setProcessedAt(LocalDateTime.now());
        refundRepository.save(refund);
    }

    @Override
    @Transactional
    public void reverseRefund(Dispute dispute) {
        // Implementation for reversing a provisional credit
    }
}
