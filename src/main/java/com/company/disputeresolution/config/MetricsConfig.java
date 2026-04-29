package com.company.disputeresolution.config;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfig {
    public MetricsConfig(MeterRegistry registry) {
        registry.counter("dispute.created.count");
        registry.counter("dispute.resolved.count");
    }
}
