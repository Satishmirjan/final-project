package com.company.disputeresolution.service;

import com.company.disputeresolution.event.DisputeEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Async
    @EventListener
    public void handleDisputeEvent(DisputeEvent event) {
        // Integrate with actual Email Service here
        System.out.println("Processing async notification for event: " + event.getEventType() + " on dispute: " + event.getDisputeId());
    }
}
