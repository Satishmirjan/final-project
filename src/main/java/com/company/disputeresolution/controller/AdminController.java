package com.company.disputeresolution.controller;

import com.company.disputeresolution.dto.response.ClaimDTO;
import com.company.disputeresolution.dto.response.DisputeDTO;
import com.company.disputeresolution.dto.response.UserDTO;
import com.company.disputeresolution.service.IAssignmentService;
import com.company.disputeresolution.service.IDisputeService;
import com.company.disputeresolution.service.IJobOrchestrationService;
import com.company.disputeresolution.service.IMetricsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final IDisputeService disputeService;
    private final IAssignmentService assignmentService;
    private final IMetricsService metricsService;
    private final IJobOrchestrationService jobOrchestrationService;

    @GetMapping("/disputes")
    public CompletionStage<ResponseEntity<List<DisputeDTO>>> getAllDisputes() {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(List.of())); // Mocked list
    }

    @GetMapping("/claims")
    public CompletionStage<ResponseEntity<List<ClaimDTO>>> getAllClaims() {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(List.of())); // Mocked list
    }

    @GetMapping("/users")
    public CompletionStage<ResponseEntity<List<UserDTO>>> getAllUsers() {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(List.of())); // Mocked list
    }

    @PostMapping("/users")
    public CompletionStage<ResponseEntity<UserDTO>> createUser(@RequestBody UserDTO userDto) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(userDto)); // Mocked
    }

    @GetMapping("/queues")
    public CompletionStage<ResponseEntity<List<ClaimDTO>>> getUnassignedQueue() {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(List.of())); // Mocked list
    }

    @PutMapping("/claims/{id}/assign")
    public CompletionStage<ResponseEntity<Void>> assignClaim(@PathVariable Long id, @RequestParam String makerId) {
        return CompletableFuture.supplyAsync(() -> {
            assignmentService.assignClaimToMaker(id, makerId);
            return ResponseEntity.accepted().build();
        });
    }

    @PutMapping("/claims/{id}/reassign")
    public CompletionStage<ResponseEntity<Void>> reassignClaim(@PathVariable Long id, @RequestParam String newMakerId) {
        return CompletableFuture.supplyAsync(() -> {
            assignmentService.reassignClaim(id, newMakerId);
            return ResponseEntity.accepted().build();
        });
    }

    @PostMapping("/transactions/ingest")
    public CompletionStage<ResponseEntity<Void>> ingestTransactions(@RequestParam("file") MultipartFile file) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.accepted().build()); // Batch mocking
    }

    @PostMapping("/jobs/{jobName}/trigger")
    public CompletionStage<ResponseEntity<Void>> triggerJob(@PathVariable String jobName) {
        return CompletableFuture.supplyAsync(() -> {
            jobOrchestrationService.triggerJob(jobName);
            return ResponseEntity.accepted().build();
        });
    }

    @GetMapping("/metrics")
    public CompletionStage<ResponseEntity<Map<String, Object>>> getMetrics() {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(metricsService.getSystemMetrics()));
    }

    @GetMapping("/reports")
    public CompletionStage<ResponseEntity<List<Object>>> getReports() {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(List.of())); // Mocked
    }

    @GetMapping("/summary")
    public CompletionStage<ResponseEntity<Map<String, Object>>> getSummary() {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(metricsService.getSystemSummary()));
    }
}
