package com.company.disputeresolution.service;

import java.util.Map;

public interface IMetricsService {
    Map<String, Object> getSystemMetrics();
    Map<String, Object> getSystemSummary();
}
