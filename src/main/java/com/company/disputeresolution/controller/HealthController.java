package com.company.disputeresolution.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@RestController
public class HealthController {

    @GetMapping("/health")
    public CompletionStage<ResponseEntity<String>> healthCheck() {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok("OK"));
    }
}
