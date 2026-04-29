package com.company.disputeresolution.controller;

import com.company.disputeresolution.dto.request.StatusUpdateRequest;
import com.company.disputeresolution.dto.response.ClaimDTO;
import com.company.disputeresolution.dto.response.DisputeDTO;
import com.company.disputeresolution.service.IClaimService;
import com.company.disputeresolution.service.IDisputeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/maker")
@RequiredArgsConstructor
public class MakerController {
    private final IClaimService claimService;
    private final IDisputeService disputeService;

    @PostMapping("/ticket/pull")
    public ResponseEntity<ClaimDTO> pullNextTicket(@RequestParam String makerId) {
        return claimService.pullNextTask(makerId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @PutMapping("/disputes/{id}/status")
    public ResponseEntity<DisputeDTO> updateDisputeStatus(
            @PathVariable Long id,
            @RequestBody StatusUpdateRequest request) {
        return ResponseEntity.ok(disputeService.updateDisputeStatus(id, request));
    }
}
