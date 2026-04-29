package com.company.disputeresolution.controller;

import com.company.disputeresolution.event.DisputeEvent;
import com.company.disputeresolution.event.EventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events/dispute")
@RequiredArgsConstructor
public class EventConsumerController {
    private final EventPublisher eventPublisher;

    @PostMapping
    public ResponseEntity<Void> consumeEvent(@RequestBody DisputeEvent event) {
        // In a real system, this might be triggered by an external message queue or webhook
        eventPublisher.publish(event);
        return ResponseEntity.accepted().build();
    }
}
