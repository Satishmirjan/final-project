package com.company.disputeresolution.service.impl;

import com.company.disputeresolution.job.DisputeReportJob;
import com.company.disputeresolution.service.IJobOrchestrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobOrchestrationServiceImpl implements IJobOrchestrationService {
    private final DisputeReportJob disputeReportJob;

    @Override
    public void triggerJob(String jobName) {
        if ("DisputeReportJob".equalsIgnoreCase(jobName)) {
            disputeReportJob.generateDailyReport();
        } else {
            throw new IllegalArgumentException("Unknown job: " + jobName);
        }
    }
}
