package org.godigit.ClientFeedbackAnalysisSystem.controller;

import org.godigit.ClientFeedbackAnalysisSystem.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/feedback/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/weekly")
    public ResponseEntity<Map<String, Object>> generateWeeklyReport() {
        Map<String, Object> report = reportService.generateReport(1);
        report = reportService.generateReport(1);
        return ResponseEntity.ok(report);
    }
   
    @GetMapping("/weekly/{weeks}")
    public ResponseEntity<Map<String, Object>> generateReport(@PathVariable int weeks) {
        Map<String, Object> report = reportService.generateReport(weeks);
        return ResponseEntity.ok(report);
    }
}