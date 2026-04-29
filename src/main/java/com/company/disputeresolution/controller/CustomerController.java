package com.company.disputeresolution.controller;

import com.company.disputeresolution.constants.ApiPaths;
import com.company.disputeresolution.dto.request.DisputeRequest;
import com.company.disputeresolution.dto.response.AuditLogDTO;
import com.company.disputeresolution.dto.response.ClaimDTO;
import com.company.disputeresolution.dto.response.DisputeDTO;
import com.company.disputeresolution.service.IClaimService;
import com.company.disputeresolution.service.IDisputeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@RestController
@RequestMapping(ApiPaths.DISPUTES)
@RequiredArgsConstructor
public class CustomerController {
    private final IClaimService claimService;
    private final IDisputeService disputeService;

    @PostMapping
    public CompletionStage<ResponseEntity<ClaimDTO>> raiseDispute(
            @RequestBody DisputeRequest request,
            @RequestHeader(value = "X-Idempotency-Key", required = false) String idempotencyKey) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(claimService.createClaim(request, idempotencyKey)));
    }

    @GetMapping("/{id}")
    public CompletionStage<ResponseEntity<DisputeDTO>> getDispute(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> disputeService.getDisputeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build()));
    }

    @GetMapping
    public CompletionStage<ResponseEntity<List<DisputeDTO>>> listDisputes(@RequestParam String customerId) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(disputeService.getCustomerDisputes(customerId)));
    }

    @GetMapping("/{id}/history")
    public CompletionStage<ResponseEntity<List<AuditLogDTO>>> getDisputeHistory(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(disputeService.getDisputeHistory(id)));
    }

    @DeleteMapping("/{id}")
    public CompletionStage<ResponseEntity<Void>> cancelDispute(@PathVariable Long id, @RequestParam String customerId) {
        return CompletableFuture.supplyAsync(() -> {
            disputeService.cancelDispute(id, customerId);
            return ResponseEntity.noContent().build();
        });
    }

    @PostMapping("/{id}/attachments")
    public CompletionStage<ResponseEntity<Void>> uploadAttachment(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        return CompletableFuture.supplyAsync(() -> {
            // Mocking file upload: just store original filename
            disputeService.addAttachment(id, file.getOriginalFilename());
            return ResponseEntity.accepted().build();
        });
    }

    @GetMapping("/{id}/attachments")
    public CompletionStage<ResponseEntity<List<String>>> getAttachments(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(disputeService.getAttachments(id)));
    }
}
