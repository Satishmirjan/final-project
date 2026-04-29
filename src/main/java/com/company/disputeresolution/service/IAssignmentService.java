package com.company.disputeresolution.service;

import com.company.disputeresolution.entity.Claim;

public interface IAssignmentService {
    void assignClaimToMaker(Long claimId, String makerId);
    void reassignClaim(Long claimId, String newMakerId);
}
