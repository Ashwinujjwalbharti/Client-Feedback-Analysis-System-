package org.godigit.ClientFeedbackAnalysisSystem.service.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import org.godigit.ClientFeedbackAnalysisSystem.models.Feedback;
import org.godigit.ClientFeedbackAnalysisSystem.repository.FeedbackRepository;
import org.godigit.ClientFeedbackAnalysisSystem.service.ReportService;
import org.springframework.stereotype.Service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.UnitValue;


import org.godigit.ClientFeedbackAnalysisSystem.models.Feedback;
import org.godigit.ClientFeedbackAnalysisSystem.repository.FeedbackRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.io.ByteArrayOutputStream;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.element.Cell;


@Service
public class ReportServiceImpl implements ReportService  {
    
    private final FeedbackRepository feedbackRepository;

    public ReportServiceImpl(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public Map<String, Object> generateReport(int weeks) {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minus(weeks, ChronoUnit.WEEKS);

        List<Feedback> feedbacks = feedbackRepository.findBySubmittedAtBetween(startDate, endDate);

        Map<String, Long> recurringIssues = getRecurringIssues(feedbacks);
        Map<String, Long> sentimentTrends = getSentimentTrends(feedbacks);

        Map<String, Object> report = new HashMap<>();
        report.put("recurringIssues", recurringIssues);
        report.put("sentimentTrends", sentimentTrends);

        return report;
    }

    public Map<String, Long> getRecurringIssues(List<Feedback> feedbacks) {
        Map<String, Long> issuesMap = new HashMap<>();
        for (Feedback feedback : feedbacks) {
            if (feedback.getCategory() != null) {
                issuesMap.put(feedback.getCategory(), issuesMap.getOrDefault(feedback.getCategory(), 0L) + 1);
            }
        }
        return issuesMap;
    }

    public Map<String, Long> getSentimentTrends(List<Feedback> feedbacks) {
        Map<String, Long> trendsMap = new HashMap<>();
        for (Feedback feedback : feedbacks) {
            if (feedback.getSentiment() != null) {
                trendsMap.put(feedback.getSentiment(), trendsMap.getOrDefault(feedback.getSentiment(), 0L) + 1);
            }
        }
        return trendsMap;
    }

    public byte[] generatePdfReport(int weeks) {
        Map<String, Object> report = generateReport(weeks);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        
        document.add(new Paragraph("Client Feedback Report - Last " + weeks + " Week(s)")
                .setBold()
                .setFontSize(16));

       
        document.add(new Paragraph("Generated on: " + LocalDateTime.now().toString())
                .setFontSize(10));

        
        Map<String, Long> issues = (Map<String, Long>) report.get("recurringIssues");
        Map<String, Long> sentiments = (Map<String, Long>) report.get("sentimentTrends");

       
        if (issues.isEmpty() && sentiments.isEmpty()) {
            document.add(new Paragraph("No feedback data available for the selected period."));
        } else {
            
            document.add(new Paragraph("\nRecurring Issues:\n").setBold());
            Table issuesTable = new Table(UnitValue.createPercentArray(new float[]{70, 30}))
                    .useAllAvailableWidth();
            issuesTable.addHeaderCell(new Cell().add(new Paragraph("Issue")).setBold());
            issuesTable.addHeaderCell(new Cell().add(new Paragraph("Count")).setBold());

            for (Map.Entry<String, Long> entry : issues.entrySet()) {
                issuesTable.addCell(new Cell().add(new Paragraph(entry.getKey())));
                issuesTable.addCell(new Cell().add(new Paragraph(String.valueOf(entry.getValue()))));
            }
            document.add(issuesTable);

            
            document.add(new Paragraph("\nSentiment Trends:\n").setBold());
            Table sentimentTable = new Table(UnitValue.createPercentArray(new float[]{70, 30}))
                    .useAllAvailableWidth();
            sentimentTable.addHeaderCell(new Cell().add(new Paragraph("Sentiment")).setBold());
            sentimentTable.addHeaderCell(new Cell().add(new Paragraph("Count")).setBold());

            for (Map.Entry<String, Long> entry : sentiments.entrySet()) {
                sentimentTable.addCell(new Cell().add(new Paragraph(entry.getKey())));
                sentimentTable.addCell(new Cell().add(new Paragraph(String.valueOf(entry.getValue()))));
            }
            document.add(sentimentTable);
        }

        document.close();
        return out.toByteArray();
    }
}
