package org.godigit.ClientFeedbackAnalysisSystem.controller;

import org.godigit.ClientFeedbackAnalysisSystem.service.ReportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/feedback/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/weekly/{weeks}")
    public ResponseEntity<?> generateReport(@PathVariable int weeks,
                                            @RequestParam(required = false) String format) {
        if ("pdf".equalsIgnoreCase(format)) {
            byte[] pdfBytes = reportService.generatePdfReport(weeks);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "feedback-report-" + weeks + "-weeks.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfBytes);
        }

        Map<String, Object> report = reportService.generateReport(weeks);
        return ResponseEntity.ok(report);
    }

    
    @GetMapping("/weekly")
    public ResponseEntity<Map<String, Object>> generateWeeklyReport() {
        Map<String, Object> report = reportService.generateReport(1);
        return ResponseEntity.ok(report);
    }
}
