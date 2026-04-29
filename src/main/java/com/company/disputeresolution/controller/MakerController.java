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
@RequestMapping("/api/maker")
@RequiredArgsConstructor
public class MakerController {
    private final IClaimService claimService;
    private final IDisputeService disputeService;

    @PostMapping("/ticket/pull")
    public CompletionStage<ResponseEntity<ClaimDTO>> pullNextTicket(@RequestParam String makerId) {
        return CompletableFuture.supplyAsync(() -> claimService.pullNextTask(makerId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build()));
    }

    @GetMapping("/tasks")
    public CompletionStage<ResponseEntity<List<ClaimDTO>>> getTasks(@RequestParam String makerId) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(List.of())); // Mocked implementation
    }

    @GetMapping("/queue/unassigned")
    public CompletionStage<ResponseEntity<List<ClaimDTO>>> getUnassignedQueue() {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(List.of())); // Mocked implementation
    }

    @GetMapping("/claims/{claimId}")
    public CompletionStage<ResponseEntity<ClaimDTO>> getClaim(@PathVariable Long claimId) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(ClaimDTO.builder().id(claimId).build())); // Mocked
    }

    @PutMapping("/disputes/{id}/phase")
    public CompletionStage<ResponseEntity<DisputeDTO>> updateDisputePhase(
            @PathVariable Long id,
            @RequestBody StatusUpdateRequest request) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(disputeService.updateDisputeStatus(id, request)));
    }

    @PostMapping("/disputes/{id}/submit")
    public CompletionStage<ResponseEntity<DisputeDTO>> submitForReview(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> {
            StatusUpdateRequest request = new StatusUpdateRequest();
            request.setNewStatus(DisputeStatus.UNDER_REVIEW);
            request.setNotes("Submitted for checker review");
            return ResponseEntity.ok(disputeService.updateDisputeStatus(id, request));
        });
    }

    @PostMapping("/disputes/{id}/escalate")
    public CompletionStage<ResponseEntity<Void>> escalateDispute(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.accepted().build());
    }

    @PostMapping("/disputes/{id}/notes")
    public CompletionStage<ResponseEntity<Void>> addNote(@PathVariable Long id, @RequestBody String note) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.accepted().build());
    }

    @GetMapping("/disputes/{id}/notes")
    public CompletionStage<ResponseEntity<List<String>>> getNotes(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(List.of("Case note 1")));
    }

    @GetMapping("/disputes/{id}")
    public CompletionStage<ResponseEntity<DisputeDTO>> getDispute(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> disputeService.getDisputeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build()));
    }
}
