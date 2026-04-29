package com.company.disputeresolution.event;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class DisputeEvent {
    private String eventType;
    private Long disputeId;
    private Long claimId;
    private String actorId;
    private LocalDateTime timestamp;
}
