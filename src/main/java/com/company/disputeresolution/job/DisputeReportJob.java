package com.company.disputeresolution.job;

import com.company.disputeresolution.entity.DisputeReport;
import com.company.disputeresolution.entity.enums.DisputeStatus;
import com.company.disputeresolution.repository.DisputeReportRepository;
import com.company.disputeresolution.repository.DisputeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DisputeReportJob {
    private final DisputeRepository disputeRepository;
    private final DisputeReportRepository reportRepository;

    @Scheduled(cron = "0 0 0 * * *") // Daily at midnight
    public void generateDailyReport() {
        DisputeReport report = new DisputeReport();
        report.setReportDate(LocalDate.now().minusDays(1));
        report.setTotalOpen(disputeRepository.findByStatus(DisputeStatus.OPEN).size());
        report.setTotalResolved(disputeRepository.findByStatus(DisputeStatus.CLOSED).size());
        // Calculate other metrics...
        reportRepository.save(report);
        System.out.println("Daily dispute report generated for: " + report.getReportDate());
    }
}
