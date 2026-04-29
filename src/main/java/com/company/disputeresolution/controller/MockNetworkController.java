package com.company.disputeresolution.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@RestController
@RequestMapping("/api/internal/mock/network")
@Profile("dev")
public class MockNetworkController {

    @PostMapping("/inbound-drop")
    public CompletionStage<ResponseEntity<String>> inboundDropMock() {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok("Mock inbound drop received"));
    }
}
