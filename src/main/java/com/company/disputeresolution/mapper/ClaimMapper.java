package com.company.disputeresolution.mapper;

import com.company.disputeresolution.dto.response.ClaimDTO;
import com.company.disputeresolution.entity.Claim;

public final class ClaimMapper {
    private ClaimMapper() {}

    public static ClaimDTO toDTO(Claim claim) {
        if (claim == null) return null;
        return ClaimDTO.builder()
                .id(claim.getId())
                .customerId(claim.getCustomer().getUserId())
                .makerId(claim.getMaker() != null ? claim.getMaker().getUserId() : null)
                .checkerId(claim.getChecker() != null ? claim.getChecker().getUserId() : null)
                .lifecycle(claim.getLifecycle())
                .totalClaimedAmount(claim.getTotalClaimedAmount())
                .regulatoryDeadline(claim.getRegulatoryDeadline())
                .createdAt(claim.getCreatedAt())
                .build();
    }
}
