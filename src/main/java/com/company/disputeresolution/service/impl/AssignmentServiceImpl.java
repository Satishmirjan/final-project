package com.company.disputeresolution.service.impl;

import com.company.disputeresolution.entity.Claim;
import com.company.disputeresolution.entity.User;
import com.company.disputeresolution.entity.enums.ClaimLifecycle;
import com.company.disputeresolution.repository.ClaimRepository;
import com.company.disputeresolution.repository.UserRepository;
import com.company.disputeresolution.service.IAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AssignmentServiceImpl implements IAssignmentService {
    private final ClaimRepository claimRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void assignClaimToMaker(Long claimId, String makerId) {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Claim not found"));
        User maker = userRepository.findByUserId(makerId)
                .orElseThrow(() -> new RuntimeException("Maker not found"));

        if (claim.getMaker() != null) {
            throw new RuntimeException("Claim is already assigned");
        }
        
        claim.setMaker(maker);
        claim.setLifecycle(ClaimLifecycle.IN_PROGRESS);
        claimRepository.save(claim);
    }

    @Override
    @Transactional
    public void reassignClaim(Long claimId, String newMakerId) {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Claim not found"));
        User maker = userRepository.findByUserId(newMakerId)
                .orElseThrow(() -> new RuntimeException("Maker not found"));

        claim.setMaker(maker);
        claimRepository.save(claim);
    }
}
