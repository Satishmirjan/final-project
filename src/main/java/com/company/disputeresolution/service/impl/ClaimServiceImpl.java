package com.company.disputeresolution.service.impl;

import com.company.disputeresolution.dto.request.DisputeRequest;
import com.company.disputeresolution.dto.response.ClaimDTO;
import com.company.disputeresolution.entity.*;
import com.company.disputeresolution.entity.enums.ClaimLifecycle;
import com.company.disputeresolution.entity.enums.DisputePhase;
import com.company.disputeresolution.entity.enums.DisputeStatus;
import com.company.disputeresolution.mapper.ClaimMapper;
import com.company.disputeresolution.repository.*;
import com.company.disputeresolution.service.IAuditLogService;
import com.company.disputeresolution.service.IClaimService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClaimServiceImpl implements IClaimService {
    private final ClaimRepository claimRepository;
    private final DisputeRepository disputeRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final RequestFingerprintRepository fingerprintRepository;
    private final IAuditLogService auditLogService;

    @Override
    @Transactional
    public ClaimDTO createClaim(DisputeRequest request, String idempotencyKey) {
        if (idempotencyKey != null) {
            Optional<RequestFingerprint> existing = fingerprintRepository.findById(idempotencyKey);
            if (existing.isPresent()) {
                return ClaimMapper.toDTO(claimRepository.findById(existing.get().getClaimId()).orElseThrow());
            }
        }

        Transaction transaction = transactionRepository.findByTransactionId(request.getTransactionId())
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        
        User customer = userRepository.findByUserId(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Claim claim = new Claim();
        claim.setCustomer(customer);
        claim.setLifecycle(ClaimLifecycle.INITIATED);
        claim.setTotalClaimedAmount(request.getAmount());
        claim.setRegulatoryDeadline(LocalDateTime.now().plusDays(90));
        claim.setCreatedAt(LocalDateTime.now());
        claim = claimRepository.save(claim);

        if (idempotencyKey != null) {
            RequestFingerprint fingerprint = new RequestFingerprint();
            fingerprint.setFingerprint(idempotencyKey);
            fingerprint.setClaimId(claim.getId());
            fingerprint.setCreatedAt(LocalDateTime.now());
            fingerprintRepository.save(fingerprint);
        }

        Dispute dispute = new Dispute();
        dispute.setClaim(claim);
        dispute.setCustomer(customer);
        dispute.setTransaction(transaction);
        dispute.setAmount(request.getAmount());
        dispute.setStatus(DisputeStatus.OPEN);
        dispute.setPhase(DisputePhase.INTAKE);
        dispute.setReason(request.getReason());
        dispute.setIntakeReasonCode(request.getIntakeReasonCode());
        dispute.setCreatedAt(LocalDateTime.now());
        dispute.setUpdatedAt(LocalDateTime.now());
        disputeRepository.save(dispute);

        auditLogService.logStatusChange(dispute, claim, customer, null, DisputeStatus.OPEN, "Dispute created via claim creation");

        return ClaimMapper.toDTO(claim);
    }

    @Override
    @Transactional
    public Optional<ClaimDTO> pullNextTask(String makerId) {
        User maker = userRepository.findByUserId(makerId)
                .orElseThrow(() -> new RuntimeException("Maker not found"));
        
        Optional<Claim> claimOpt = claimRepository.findNextUnassignedClaim();
        if (claimOpt.isPresent()) {
            Claim claim = claimOpt.get();
            claim.setMaker(maker);
            claim.setLifecycle(ClaimLifecycle.IN_PROGRESS);
            return Optional.of(ClaimMapper.toDTO(claimRepository.save(claim)));
        }
        return Optional.empty();
    }
}
