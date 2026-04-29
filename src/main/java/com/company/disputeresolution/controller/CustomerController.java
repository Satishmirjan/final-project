package com.company.disputeresolution.controller;

import com.company.disputeresolution.constants.ApiPaths;
import com.company.disputeresolution.dto.request.DisputeRequest;
import com.company.disputeresolution.dto.response.ClaimDTO;
import com.company.disputeresolution.dto.response.DisputeDTO;
import com.company.disputeresolution.service.IClaimService;
import com.company.disputeresolution.service.IDisputeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(ApiPaths.DISPUTES)
@RequiredArgsConstructor
public class CustomerController {
    private final IClaimService claimService;
    private final IDisputeService disputeService;

    @PostMapping
    public ResponseEntity<ClaimDTO> raiseDispute(
            @RequestBody DisputeRequest request,
            @RequestHeader(value = "X-Idempotency-Key", required = false) String idempotencyKey) {
        return ResponseEntity.ok(claimService.createClaim(request, idempotencyKey));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisputeDTO> getDispute(@PathVariable Long id) {
        return disputeService.getDisputeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<DisputeDTO>> listDisputes(@RequestParam String customerId) {
        return ResponseEntity.ok(disputeService.getCustomerDisputes(customerId));
    }
}
