package com.company.disputeresolution.controller;

import com.company.disputeresolution.dto.request.StatusUpdateRequest;
import com.company.disputeresolution.dto.response.DisputeDTO;
import com.company.disputeresolution.service.IDisputeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checker")
@RequiredArgsConstructor
public class CheckerController {
    private final IDisputeService disputeService;

    @PutMapping("/disputes/{id}/decide")
    public ResponseEntity<DisputeDTO> decideDispute(
            @PathVariable Long id,
            @RequestBody StatusUpdateRequest request) {
        return ResponseEntity.ok(disputeService.updateDisputeStatus(id, request));
    }
}
