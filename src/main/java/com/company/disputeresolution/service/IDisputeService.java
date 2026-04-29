package com.company.disputeresolution.service;

import com.company.disputeresolution.dto.request.StatusUpdateRequest;
import com.company.disputeresolution.dto.response.DisputeDTO;
import com.company.disputeresolution.entity.enums.DisputeStatus;
import java.util.List;
import java.util.Optional;

public interface IDisputeService {
    Optional<DisputeDTO> getDisputeById(Long id);
    List<DisputeDTO> getCustomerDisputes(String customerId);
    DisputeDTO updateDisputeStatus(Long id, StatusUpdateRequest request);
    List<DisputeDTO> getDisputesByStatus(DisputeStatus status);
}
