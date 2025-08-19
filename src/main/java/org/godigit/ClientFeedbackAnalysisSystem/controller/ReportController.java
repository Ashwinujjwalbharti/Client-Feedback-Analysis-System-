package org.godigit.ClientFeedbackAnalysisSystem.controller;

import org.godigit.ClientFeedbackAnalysisSystem.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/feedback/reports")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/weekly")
    public ResponseEntity<Map<String, Object>> generateWeeklyReport() {
        Map<String, Object> report = reportService.generateWeeklyReport();
        return ResponseEntity.ok(report);
    }
}