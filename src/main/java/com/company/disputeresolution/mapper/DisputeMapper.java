package com.company.disputeresolution.mapper;

import com.company.disputeresolution.dto.response.DisputeDTO;
import com.company.disputeresolution.entity.Dispute;

public final class DisputeMapper {
    private DisputeMapper() {}

    public static DisputeDTO toDTO(Dispute dispute) {
        if (dispute == null) return null;
        return DisputeDTO.builder()
                .id(dispute.getId())
                .claimId(dispute.getClaim().getId())
                .customerId(dispute.getCustomer().getUserId())
                .transactionId(dispute.getTransaction().getTransactionId())
                .makerId(dispute.getMaker() != null ? dispute.getMaker().getUserId() : null)
                .checkerId(dispute.getChecker() != null ? dispute.getChecker().getUserId() : null)
                .amount(dispute.getAmount())
                .status(dispute.getStatus())
                .phase(dispute.getPhase())
                .reason(dispute.getReason())
                .createdAt(dispute.getCreatedAt())
                .updatedAt(dispute.getUpdatedAt())
                .build();
    }
}
