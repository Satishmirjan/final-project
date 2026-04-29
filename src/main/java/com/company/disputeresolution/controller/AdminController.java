package com.company.disputeresolution.controller;

import com.company.disputeresolution.dto.response.DisputeDTO;
import com.company.disputeresolution.entity.enums.DisputeStatus;
import com.company.disputeresolution.service.IDisputeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final IDisputeService disputeService;

    @GetMapping("/disputes")
    public ResponseEntity<List<DisputeDTO>> getAllDisputes(@RequestParam(required = false) DisputeStatus status) {
        if (status != null) {
            return ResponseEntity.ok(disputeService.getDisputesByStatus(status));
        }
        // Simplified for now
        return ResponseEntity.ok(List.of());
    }
}
