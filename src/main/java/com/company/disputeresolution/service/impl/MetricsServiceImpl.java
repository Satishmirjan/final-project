package com.company.disputeresolution.service.impl;

import com.company.disputeresolution.repository.ClaimRepository;
import com.company.disputeresolution.repository.DisputeRepository;
import com.company.disputeresolution.service.IMetricsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MetricsServiceImpl implements IMetricsService {
    private final DisputeRepository disputeRepository;
    private final ClaimRepository claimRepository;

    @Override
    public Map<String, Object> getSystemMetrics() {
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("totalDisputes", disputeRepository.count());
        metrics.put("totalClaims", claimRepository.count());
        return metrics;
    }

    @Override
    public Map<String, Object> getSystemSummary() {
        Map<String, Object> summary = new HashMap<>();
        summary.put("status", "System runs normally");
        summary.put("activeDisputes", disputeRepository.count()); // simplified
        return summary;
    }
}
