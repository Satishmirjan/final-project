package com.company.disputeresolution.service;

import com.company.disputeresolution.dto.request.DisputeRequest;
import com.company.disputeresolution.dto.response.ClaimDTO;
import java.util.Optional;

public interface IClaimService {
    ClaimDTO createClaim(DisputeRequest request, String idempotencyKey);
    Optional<ClaimDTO> pullNextTask(String makerId);
}
