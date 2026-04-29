package com.company.disputeresolution.service;

import com.company.disputeresolution.dto.request.StatusUpdateRequest;
import com.company.disputeresolution.dto.response.DisputeDTO;
import com.company.disputeresolution.entity.enums.DisputeStatus;
import java.util.List;
import java.util.Optional;

public interface IDisputeService {
    Optional<DisputeDTO> getDisputeById(Long id);
    List<DisputeDTO> getCustomerDisputes(String customerId);
    List<DisputeDTO> getDisputesByStatus(DisputeStatus status);
    DisputeDTO updateDisputeStatus(Long id, com.company.disputeresolution.dto.request.StatusUpdateRequest request);
    List<com.company.disputeresolution.dto.response.AuditLogDTO> getDisputeHistory(Long id);
    void cancelDispute(Long id, String customerId);
    void addAttachment(Long id, String fileUrl);
    List<String> getAttachments(Long id);
}
