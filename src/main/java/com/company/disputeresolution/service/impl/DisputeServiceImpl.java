package com.company.disputeresolution.service.impl;

import com.company.disputeresolution.dto.request.StatusUpdateRequest;
import com.company.disputeresolution.dto.response.DisputeDTO;
import com.company.disputeresolution.entity.Dispute;
import com.company.disputeresolution.entity.User;
import com.company.disputeresolution.entity.enums.DisputePhase;
import com.company.disputeresolution.entity.enums.DisputeStatus;
import com.company.disputeresolution.exception.InvalidStateTransitionException;
import com.company.disputeresolution.mapper.DisputeMapper;
import com.company.disputeresolution.repository.DisputeRepository;
import com.company.disputeresolution.repository.UserRepository;
import com.company.disputeresolution.service.IAuditLogService;
import com.company.disputeresolution.service.IDisputeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DisputeServiceImpl implements IDisputeService {
    private final DisputeRepository disputeRepository;
    private final UserRepository userRepository;
    private final IAuditLogService auditLogService;
    private final com.company.disputeresolution.repository.DisputeAuditLogRepository auditLogRepo;
    private final com.company.disputeresolution.event.EventPublisher eventPublisher;
    private final com.company.disputeresolution.service.IRefundService refundService;

    // Temporary mock storage for attachments
    private final java.util.Map<Long, List<String>> temporaryAttachmentStorage = new java.util.concurrent.ConcurrentHashMap<>();

    @Override
    public Optional<DisputeDTO> getDisputeById(Long id) {
        return disputeRepository.findById(id).map(DisputeMapper::toDTO);
    }

    @Override
    public List<DisputeDTO> getCustomerDisputes(String customerId) {
        User customer = userRepository.findByUserId(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return disputeRepository.findByCustomer(customer).stream()
                .map(DisputeMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DisputeDTO updateDisputeStatus(Long id, StatusUpdateRequest request) {
        Dispute dispute = disputeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dispute not found"));
        
        User user = userRepository.findByUserId(request.getChangedByUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        validateTransition(dispute.getStatus(), request.getNewStatus());

        DisputeStatus oldStatus = dispute.getStatus();
        dispute.setStatus(request.getNewStatus());
        dispute.setUpdatedAt(LocalDateTime.now());
        
        // Update phase based on status
        updatePhase(dispute, request.getNewStatus());

        dispute = disputeRepository.save(dispute);
        auditLogService.logStatusChange(dispute, dispute.getClaim(), user, oldStatus, request.getNewStatus(), request.getNotes());

        // Publish Event
        eventPublisher.publish(com.company.disputeresolution.event.DisputeEvent.builder()
                .eventType(com.company.disputeresolution.constants.EventTypes.DISPUTE_UPDATED)
                .disputeId(dispute.getId())
                .claimId(dispute.getClaim().getId())
                .actorId(user.getUserId())
                .timestamp(LocalDateTime.now())
                .build());

        return DisputeMapper.toDTO(dispute);
    }

    @Override
    public List<DisputeDTO> getDisputesByStatus(DisputeStatus status) {
        return disputeRepository.findByStatus(status).stream()
                .map(DisputeMapper::toDTO)
                .collect(Collectors.toList());
    }

    private void validateTransition(DisputeStatus current, DisputeStatus next) {
        if (current == DisputeStatus.CLOSED) {
            throw new InvalidStateTransitionException("Cannot transition from CLOSED state");
        }
        if (current == DisputeStatus.OPEN && next != DisputeStatus.UNDER_REVIEW && next != DisputeStatus.CLOSED) {
            throw new InvalidStateTransitionException("Invalid transition from OPEN to " + next);
        }
        // Add more transition rules as needed
    }

    private void updatePhase(Dispute dispute, DisputeStatus newStatus) {
        switch (newStatus) {
            case UNDER_REVIEW -> dispute.setPhase(DisputePhase.INVESTIGATION);
            case APPROVED -> {
                dispute.setPhase(DisputePhase.RESOLUTION);
                refundService.processRefund(dispute.getClaim(), dispute, dispute.getAmount(), false);
            }
            case REJECTED -> {
                dispute.setPhase(DisputePhase.RESOLUTION);
                refundService.reverseRefund(dispute);
            }
            case CLOSED -> dispute.setPhase(DisputePhase.RESOLUTION);
        }
    }

    @Override
    public List<com.company.disputeresolution.dto.response.AuditLogDTO> getDisputeHistory(Long id) {
        Dispute dispute = disputeRepository.findById(id).orElseThrow(() -> new RuntimeException("Dispute not found"));
        return auditLogRepo.findByDisputeOrderByChangedAtDesc(dispute).stream()
                .map(com.company.disputeresolution.mapper.AuditLogMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void cancelDispute(Long id, String customerId) {
        Dispute dispute = disputeRepository.findById(id).orElseThrow(() -> new RuntimeException("Dispute not found"));
        if (!dispute.getCustomer().getUserId().equals(customerId)) {
            throw new RuntimeException("Unauthorized");
        }
        if (dispute.getStatus() != DisputeStatus.OPEN) {
            throw new InvalidStateTransitionException("Only OPEN disputes can be cancelled");
        }
        DisputeStatus oldStatus = dispute.getStatus();
        dispute.setStatus(DisputeStatus.CLOSED);
        dispute.setPhase(DisputePhase.RESOLUTION);
        dispute.setUpdatedAt(LocalDateTime.now());
        disputeRepository.save(dispute);
        
        auditLogService.logStatusChange(dispute, dispute.getClaim(), dispute.getCustomer(), oldStatus, DisputeStatus.CLOSED, "Cancelled by Customer");
    }

    @Override
    public void addAttachment(Long id, String fileUrl) {
        temporaryAttachmentStorage.computeIfAbsent(id, k -> new java.util.ArrayList<>()).add(fileUrl);
    }

    @Override
    public List<String> getAttachments(Long id) {
        return temporaryAttachmentStorage.getOrDefault(id, List.of());
    }
}
