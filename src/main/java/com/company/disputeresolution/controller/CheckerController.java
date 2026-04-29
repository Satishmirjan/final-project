package com.company.disputeresolution.controller;

import com.company.disputeresolution.dto.request.StatusUpdateRequest;
import com.company.disputeresolution.dto.response.ClaimDTO;
import com.company.disputeresolution.dto.response.DisputeDTO;
import com.company.disputeresolution.entity.enums.DisputeStatus;
import com.company.disputeresolution.service.IClaimService;
import com.company.disputeresolution.service.IDisputeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@RestController
@RequestMapping("/api/checker")
@RequiredArgsConstructor
public class CheckerController {
    private final IDisputeService disputeService;
    private final IClaimService claimService;

    @PostMapping("/ticket/pull")
    public CompletionStage<ResponseEntity<ClaimDTO>> pullNextTicket(@RequestParam String checkerId) {
        // Simplified mapping; realistically there might be a separate pull for checkers
        return CompletableFuture.supplyAsync(() -> claimService.pullNextTask(checkerId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build()));
    }

    @GetMapping("/tasks")
    public CompletionStage<ResponseEntity<List<ClaimDTO>>> getCheckerTasks(@RequestParam String checkerId) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(List.of())); // Mocked Tasks
    }

    @GetMapping("/claims/{claimId}")
    public CompletionStage<ResponseEntity<ClaimDTO>> getClaim(@PathVariable Long claimId) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(ClaimDTO.builder().id(claimId).build())); // Mocked
    }

    @PutMapping("/disputes/{id}/decide")
    public CompletionStage<ResponseEntity<DisputeDTO>> decideDispute(
            @PathVariable Long id,
            @RequestBody StatusUpdateRequest request) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(disputeService.updateDisputeStatus(id, request)));
    }
}
